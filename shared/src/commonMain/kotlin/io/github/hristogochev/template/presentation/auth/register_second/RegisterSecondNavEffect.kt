package io.github.hristogochev.template.presentation.auth.register_second

import io.github.hristogochev.template.presentation.auth.register_success.RegisterSuccessState

sealed interface RegisterSecondNavEffect {
    data object Back : RegisterSecondNavEffect
    data class Continue(
        val initialState: RegisterSuccessState,
    ) : RegisterSecondNavEffect
}