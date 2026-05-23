package io.github.hristogochev.template.presentation.auth.register_second


sealed interface RegisterSecondEvent {
    data class SendAgain(val phoneNumber: String) : RegisterSecondEvent
    data class UpdateCode(val value: String) : RegisterSecondEvent
}