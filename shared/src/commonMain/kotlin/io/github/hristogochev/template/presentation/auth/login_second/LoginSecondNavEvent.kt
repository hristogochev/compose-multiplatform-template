package io.github.hristogochev.template.presentation.auth.login_second

sealed interface LoginSecondNavEvent {
    data object Back : LoginSecondNavEvent
    data class Continue(val phoneNumber: String, val code: String) : LoginSecondNavEvent
}