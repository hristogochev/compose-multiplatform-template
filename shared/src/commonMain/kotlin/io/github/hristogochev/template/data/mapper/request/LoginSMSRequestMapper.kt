package io.github.hristogochev.template.data.mapper.request

import io.github.hristogochev.template.data.entity.request.LoginSMSRequestEntity
import io.github.hristogochev.template.domain.entity.request.LoginSMSRequest

fun LoginSMSRequest.toLoginSMSRequestEntity() = LoginSMSRequestEntity(
    phoneNumber = phoneNumber,
)