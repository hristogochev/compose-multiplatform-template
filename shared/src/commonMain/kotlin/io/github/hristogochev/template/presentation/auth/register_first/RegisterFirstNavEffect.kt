package io.github.hristogochev.template.presentation.auth.register_first

import io.github.hristogochev.template.presentation.auth.login_first.LoginFirstState
import io.github.hristogochev.template.presentation.auth.register_second.RegisterSecondState

sealed interface RegisterFirstNavEffect {
    data class Login(val initialState: LoginFirstState) : RegisterFirstNavEffect
    data class Continue(val initialState: RegisterSecondState) : RegisterFirstNavEffect
}