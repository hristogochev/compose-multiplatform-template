package io.github.hristogochev.template.data.mapper.request

import io.github.hristogochev.template.data.entity.request.RegisteredRequestEntity
import io.github.hristogochev.template.domain.entity.request.RegisteredRequest

fun RegisteredRequest.toRegisteredRequestEntity() = RegisteredRequestEntity(
    phoneNumber = phoneNumber
)

