package io.github.hristogochev.template.presentation.auth.login_first

import io.github.hristogochev.template.presentation.auth.login_second.LoginSecondState
import io.github.hristogochev.template.presentation.auth.register_first.RegisterFirstState

sealed interface LoginFirstNavEffect {
    data class Register(val initialState: RegisterFirstState) : LoginFirstNavEffect
    data class Continue(val initialState: LoginSecondState) : LoginFirstNavEffect
}