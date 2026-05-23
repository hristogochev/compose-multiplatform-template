package io.github.hristogochev.template.presentation.auth.register_success

import io.github.hristogochev.template.domain.entity.auth.UserCredentials

sealed interface RegisterSuccessNavEvent {
    data class Continue(val credentials: UserCredentials) : RegisterSuccessNavEvent
}