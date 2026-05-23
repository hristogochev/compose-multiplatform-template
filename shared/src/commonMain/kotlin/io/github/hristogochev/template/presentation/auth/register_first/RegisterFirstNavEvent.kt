package io.github.hristogochev.template.presentation.auth.register_first


sealed interface RegisterFirstNavEvent {
    data class Login(val phoneNumber: String) : RegisterFirstNavEvent
    data class Continue(val phoneNumber: String) : RegisterFirstNavEvent
}