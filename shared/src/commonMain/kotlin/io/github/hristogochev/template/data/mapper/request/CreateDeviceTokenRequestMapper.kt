package io.github.hristogochev.template.data.mapper.request

import io.github.hristogochev.template.data.entity.request.CreateDeviceTokenRequestEntity
import io.github.hristogochev.template.domain.entity.request.CreateDeviceTokenRequest

fun CreateDeviceTokenRequest.toEntity(): CreateDeviceTokenRequestEntity {
    return CreateDeviceTokenRequestEntity(deviceToken)
}