@file:OptIn(ExperimentalCoroutinesApi::class)

package io.github.hristogochev.template.presentation.home

import io.github.hristogochev.template.domain.validator.validateDeviceToken
import io.github.hristogochev.template.presentation.main.MainMessage
import io.github.hristogochev.template.presentation.main.MainMessageCommunicator
import io.github.hristogochev.template.util.notification.PushNotificationManager
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.template.util.updateEffect
import io.github.hristogochev.template.util.updateNavEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mainMessageCommunicator: MainMessageCommunicator,
    navEffectCommunicator: HomeNavEffectCommunicator,
    messageCommunicator: HomeMessageCommunicator,
    initialState: HomeState,
) : ViewModel<HomeState, HomeEvent, HomeEffect, HomeNavEvent, HomeNavEffect>(initialState) {

    init {
        navEffectCommunicator.consume(viewModelScope) {
            _navEffect.updateNavEffect(it)
        }

        messageCommunicator.consume(viewModelScope) {
            when (it) {
                HomeMessage.ConnectionError -> {
                    _effect.updateEffect(HomeEffect.ConnectionError)
                }

                HomeMessage.SomethingWentWrong -> {
                    _effect.updateEffect(HomeEffect.SomethingWentWrong)
                }
            }
        }

        viewModelScope.launch {
            PushNotificationManager.shared.apnsTokenSet.first { it }

            val deviceToken = PushNotificationManager.shared.getToken()
                ?.takeIf { deviceToken -> deviceToken.validateDeviceToken() == null }

            PushNotificationManager.shared.updateDeviceToken(deviceToken)
        }
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnPostNotificationPermission -> {
                viewModelScope.launch here@{
                    if (!event.granted) {
                        PushNotificationManager.shared.updateDeviceToken(null)
                        return@here
                    }

                    PushNotificationManager.shared.apnsTokenSet.first { it }

                    val deviceToken = PushNotificationManager.shared.getToken()
                        ?.takeIf { deviceToken -> deviceToken.validateDeviceToken() == null }

                    PushNotificationManager.shared.updateDeviceToken(deviceToken)
                }
            }
        }
    }

    override suspend fun onNavEventProtected(event: HomeNavEvent) {
        when (event) {
            HomeNavEvent.OnLogout -> {
                mainMessageCommunicator.sendMessage(MainMessage.RequestLogout)
            }
        }
    }
}