package io.github.hristogochev.template.presentation.auth.landing

import io.github.hristogochev.template.presentation.auth.login_first.LoginFirstState
import io.github.hristogochev.template.presentation.auth.register_first.RegisterFirstState

sealed interface LandingNavEffect {
    data class Register(val initialState: RegisterFirstState) : LandingNavEffect
    data class Login(val initialState: LoginFirstState) : LandingNavEffect
}