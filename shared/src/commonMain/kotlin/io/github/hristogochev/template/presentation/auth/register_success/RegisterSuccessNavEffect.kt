package io.github.hristogochev.template.presentation.auth.register_success

import io.github.hristogochev.template.presentation.home.HomeState

sealed interface RegisterSuccessNavEffect {
    data class Continue(
        val homeInitialState: HomeState,
    ) :
        RegisterSuccessNavEffect
}