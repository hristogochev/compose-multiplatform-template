package io.github.hristogochev.template.presentation.auth.login_second


sealed interface LoginSecondEvent {
    data class SendAgain(val phoneNumber: String) : LoginSecondEvent
    data class UpdateCode(val value: String) : LoginSecondEvent
}