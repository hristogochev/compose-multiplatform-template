package io.github.hristogochev.template.presentation.auth.register_second

sealed interface RegisterSecondNavEvent {
    data object Back : RegisterSecondNavEvent
    data class Continue(val phoneNumber: String, val code: String) : RegisterSecondNavEvent
}