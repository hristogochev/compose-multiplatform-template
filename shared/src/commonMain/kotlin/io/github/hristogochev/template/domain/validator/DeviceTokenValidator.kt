package io.github.hristogochev.template.domain.validator

import io.github.hristogochev.template.domain.error.DeviceTokenError

fun String.validateDeviceToken(): DeviceTokenError?{
    val value = this.trim()
    if (value.isEmpty()) return DeviceTokenError.Empty
    if (value.length > 255) return DeviceTokenError.MustBeLessThan256Letters
    return null
}