package io.github.hristogochev.template.data.mapper.request

import io.github.hristogochev.template.data.entity.request.RegisterSMSRequestEntity
import io.github.hristogochev.template.domain.entity.request.RegisterSMSRequest


fun RegisterSMSRequest.toRegisterSMSRequestEntity() = RegisterSMSRequestEntity(
    phoneNumber = phoneNumber,
)