package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.RegisteredResponseEntity
import io.github.hristogochev.template.domain.entity.response.RegisteredResponse


fun RegisteredResponseEntity.toRegisteredResponse() = RegisteredResponse(
    registered = registered
)

