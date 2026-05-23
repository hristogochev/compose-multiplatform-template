package io.github.hristogochev.template.presentation.main

import io.github.hristogochev.template.presentation.auth.landing.LandingState
import io.github.hristogochev.template.presentation.auth.register_success.RegisterSuccessState
import io.github.hristogochev.template.presentation.home.HomeState

sealed interface MainNavEffect {
    data class Landing(val initialState: LandingState) : MainNavEffect
}