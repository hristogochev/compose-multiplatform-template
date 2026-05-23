package io.github.hristogochev.template.presentation.auth.login_second

import io.github.hristogochev.template.presentation.home.HomeState

sealed interface LoginSecondNavEffect {
    data object Back : LoginSecondNavEffect
    data class Continue(
        val homeInitialState: HomeState,
    ) : LoginSecondNavEffect
}