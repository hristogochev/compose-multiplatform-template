package io.github.hristogochev.template.presentation.main

import io.github.hristogochev.perk.permission.Permission
import io.github.hristogochev.template.domain.entity.request.CreateDeviceTokenRequest
import io.github.hristogochev.template.domain.repository.FirstLoginNotificationPermissionRequestedRepository
import io.github.hristogochev.template.domain.repository.RemoteRepository
import io.github.hristogochev.template.domain.repository.SkippedVersionRepository
import io.github.hristogochev.template.domain.repository.UserRepository
import io.github.hristogochev.template.domain.validator.validateDeviceToken
import io.github.hristogochev.template.presentation.app.AppPrepared
import io.github.hristogochev.template.presentation.auth.landing.LandingState
import io.github.hristogochev.template.presentation.update.NeedsUpdateApiOrApp
import io.github.hristogochev.template.util.ApiResponse
import io.github.hristogochev.template.util.VersionCodeGetter
import io.github.hristogochev.template.util.getOrNull
import io.github.hristogochev.template.util.isIOS
import io.github.hristogochev.template.util.notification.EventNotificationManager
import io.github.hristogochev.template.util.notification.LocalNotificationManager
import io.github.hristogochev.template.util.notification.PushNotificationManager
import io.github.hristogochev.template.util.onEachEffect
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.template.util.updateNavEffect
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(
    private val userRepository: UserRepository,
    private val remoteRepository: RemoteRepository,
    private val versionCodeGetter: VersionCodeGetter,
    private val skippedVersionRepository: SkippedVersionRepository,
    private val firstLoginNotificationPermissionRequestedRepository: FirstLoginNotificationPermissionRequestedRepository,
    messageCommunicator: MainMessageCommunicator,
    initialState: MainState,
) : ViewModel<MainState, MainEvent, MainEffect, MainNavEvent, MainNavEffect>(initialState) {

    companion object {
        private const val API_VERSION_CODE: ULong = 1u
    }

    private val userDeviceToken =
        combine(
            PushNotificationManager.shared.deviceToken,
            userRepository.credentials
        ) { deviceToken, userAccessToken ->
            Pair(deviceToken, userAccessToken)
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            Pair(null, userRepository.credentials.value)
        )


    init {
        // Message handling
        messageCommunicator.consume(viewModelScope) { message ->
            when (message) {
                MainMessage.RequestLogout -> {
                    onRequestLogout()
                }
            }
        }

        // Version check
        viewModelScope.launch {
            val (apiOrApp, iosAppId) = versionCheck() ?: return@launch

            if (apiOrApp is NeedsUpdateApiOrApp.Api) {
                _state.update { it.copy(needsUpdate = UpdateState(iosAppId)) }
            }
        }

        // Session expired check
        viewModelScope.launch {
            if (initialState.prepared !is AppPrepared.Prepared.Home) {
                return@launch
            }

            val response =
                remoteRepository.getStatus(initialState.prepared.homeState.credentials.token)

            if (response !is ApiResponse.Error.HttpError.Unauthorized) {
                return@launch
            }

            PushNotificationManager.shared.deleteMyToken()

            userRepository.clear()

            _state.update { it.copy(sessionExpired = true) }
        }

        // Show notifications/notification dialogs
        EventNotificationManager.shared.onNotificationReceivedInForeground.onEachEffect(
            viewModelScope
        ) { foregroundNotification ->
            val (content, data) = foregroundNotification

            val shouldShowNotificationOrDialog =
                getShouldShowNotificationOrDialog(data)

            when (shouldShowNotificationOrDialog) {
                ShouldShowNotificationOrDialog.Dialog -> {
                    val id = Random.nextInt(0, Int.MAX_VALUE)
                    val dialogTitle = data["dialog_title"]
                    val dialogBody = data["dialog_body"]
                    val dialogOnClickText = data["dialog_on_click_text"]
                    val dialogOnDismissText = data["dialog_on_dismiss_text"]

                    val notificationDialog =
                        NotificationDialog(
                            id = id,
                            dialogTitle = dialogTitle,
                            dialogBody = dialogBody,
                            dialogOnClickText = dialogOnClickText,
                            dialogOnDismissText = dialogOnDismissText,
                            content = content,
                            data = data
                        )

                    _state.update { state ->
                        val newNotificationDialogs = state.notificationDialogs + notificationDialog
                        state.copy(notificationDialogs = newNotificationDialogs)
                    }
                }

                ShouldShowNotificationOrDialog.Notification -> {
                    val title = content.title
                    val body = content.body
                    LocalNotificationManager.shared.notify(
                        title = title,
                        body = body,
                        payloadData = data
                    )
                }
            }
        }

        // Navigate somewhere on notification clicked
        EventNotificationManager.shared.onNotificationClicked.onEachEffect(viewModelScope) {

        }

        userDeviceToken.onEach {
            val deviceToken = it.first ?: return@onEach

            val credentials = it.second ?: return@onEach

            val request = CreateDeviceTokenRequest(deviceToken)

            remoteRepository.createDeviceToken(credentials.token, request)
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.HideLoggingOutSheet -> _state.update { it.copy(loggingOut = false) }

            is MainEvent.NotificationDialogDismiss -> {
                onNotificationDialogDismiss(event.id)
            }

            MainEvent.ShowLoggingOutSheet -> {
                onRequestLogout()
            }
        }
    }

    override suspend fun onNavEventProtected(event: MainNavEvent) {
        when (event) {
            MainNavEvent.HideSessionExpiredDialog -> {
                onHideSessionExpiredDialog()
            }

            MainNavEvent.Logout -> {
                onLogout()
            }

            is MainNavEvent.NotificationDialogClicked -> {
                onNotificationDialogClicked(event.id, event.data)
            }
        }
    }

    private fun onRequestLogout() {
        _state.update { it.copy(loggingOut = true) }
    }

    // Session expired
    private fun onHideSessionExpiredDialog() {
        _state.update { it.copy(sessionExpired = false) }

        _navEffect.updateNavEffect(MainNavEffect.Landing(LandingState))
    }


    // Logout
    private suspend fun onLogout() {
        PushNotificationManager.shared.deleteMyToken()
        userRepository.clear()
        _state.update { it.copy(loggingOut = false) }
        _navEffect.updateNavEffect(MainNavEffect.Landing(LandingState))
    }


    // Notifications/Notification dialogs
    private fun getShouldShowNotificationOrDialog(
        data: Map<String, String>,
    ): ShouldShowNotificationOrDialog {
        return ShouldShowNotificationOrDialog.Notification
    }

    private fun onNotificationDialogDismiss(id: Int) {
        _state.update { state ->
            val newNotificationDialogs = state.notificationDialogs.filter {
                it.id != id
            }
            state.copy(notificationDialogs = newNotificationDialogs)
        }
    }

    private fun onNotificationDialogClicked(
        id: Int,
        data: Map<String, String>,
    ) {
        _state.update { state ->
            val newNotificationDialogs = state.notificationDialogs.filter {
                it.id != id
            }
            state.copy(notificationDialogs = newNotificationDialogs)
        }
        EventNotificationManager.shared.notificationClicked(data)
    }

    private enum class ShouldShowNotificationOrDialog {
        Dialog, Notification
    }

    // Version check
    private suspend fun versionCheck(): Pair<NeedsUpdateApiOrApp, String>? {
        val remoteVersion = remoteRepository.getVersion().getOrNull()
        if (remoteVersion == null || !(!isIOS || remoteVersion.iOSAppId.isNotEmpty())) return null

        if (remoteVersion.apiVersionCode > API_VERSION_CODE) {
            val apiOrApp = NeedsUpdateApiOrApp.Api(API_VERSION_CODE, remoteVersion.apiVersionCode)
            val iOSAppId = remoteVersion.iOSAppId

            return Pair(apiOrApp, iOSAppId)
        }

        val appVersionCode = versionCodeGetter.getVersionCode() ?: return null

        if (remoteVersion.appVersionCode > appVersionCode) {
            val skippedVersion = skippedVersionRepository.get()

            if (skippedVersion == remoteVersion.appVersionCode) return null


            val apiOrApp = NeedsUpdateApiOrApp.App(
                localVersionCode = appVersionCode,
                remoteVersionCode = remoteVersion.appVersionCode,
            )

            val iOSAppId = remoteVersion.iOSAppId

            return Pair(apiOrApp, iOSAppId)
        }

        return null
    }
}


