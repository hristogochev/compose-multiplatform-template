package io.github.hristogochev.template.presentation.auth.register_success

import io.github.hristogochev.template.domain.entity.auth.UserCredentials
import io.github.hristogochev.template.presentation.home.HomeState
import io.github.hristogochev.template.presentation.main.MainMessage
import io.github.hristogochev.template.presentation.main.MainMessageCommunicator
import io.github.hristogochev.template.presentation.main.MainNavEffect
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.template.util.updateNavEffect

class RegisterSuccessViewModel(
    initialState: RegisterSuccessState,
) :
    ViewModel<RegisterSuccessState, Nothing, Nothing, RegisterSuccessNavEvent, RegisterSuccessNavEffect>(
        initialState
    ) {


    override suspend fun onNavEventProtected(event: RegisterSuccessNavEvent) {
        when (event) {
            is RegisterSuccessNavEvent.Continue -> {
                onContinue(event.credentials)
            }
        }
    }


    private suspend fun onContinue(credentials: UserCredentials) {
        val initialState = HomeState(credentials)

        _navEffect.updateNavEffect(RegisterSuccessNavEffect.Continue(initialState))
    }
}