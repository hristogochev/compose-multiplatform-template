package io.github.hristogochev.template.presentation.auth.login_first


sealed interface LoginFirstNavEvent {
    data class Register(val phoneNumber: String) : LoginFirstNavEvent
    data class Continue(val phoneNumber: String) : LoginFirstNavEvent
}
