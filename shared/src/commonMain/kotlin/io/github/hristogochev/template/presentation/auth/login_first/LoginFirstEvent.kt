package io.github.hristogochev.template.presentation.auth.login_first

sealed interface LoginFirstEvent {
    data class UpdatePhoneNumber(val value: String) : LoginFirstEvent
}