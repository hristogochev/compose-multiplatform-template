package io.github.hristogochev.template.presentation.auth.register_first

sealed interface RegisterFirstEvent {
    data class UpdatePhoneNumber(val value: String) : RegisterFirstEvent
}