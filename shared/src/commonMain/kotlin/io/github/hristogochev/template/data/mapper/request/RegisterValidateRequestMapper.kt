package io.github.hristogochev.template.data.mapper.request

import io.github.hristogochev.template.data.entity.request.RegisterValidateRequestEntity
import io.github.hristogochev.template.domain.entity.request.RegisterValidateRequest


fun RegisterValidateRequest.toRegisterValidateRequestEntity() = RegisterValidateRequestEntity(
    phoneNumber = phoneNumber,
    code = code
)