package io.github.hristogochev.template.data.mapper.request


import io.github.hristogochev.template.data.entity.request.LoginValidateRequestEntity
import io.github.hristogochev.template.domain.entity.request.LoginValidateRequest

fun LoginValidateRequest.toLoginValidateRequestEntity() = LoginValidateRequestEntity(
    phoneNumber = phoneNumber,
    code = code
)