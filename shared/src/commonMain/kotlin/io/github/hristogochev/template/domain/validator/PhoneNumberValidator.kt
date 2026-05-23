package io.github.hristogochev.template.domain.validator

import io.github.hristogochev.template.domain.error.PhoneNumberError
import io.github.hristogochev.template.util.matchPhoneNumber

fun String.validatePhoneNumber(): PhoneNumberError? {
    val value = this
    if (value.isEmpty()) return PhoneNumberError.Empty
    if (!matchPhoneNumber(value)) return PhoneNumberError.Invalid
    if (!(value.startsWith("359") || value.startsWith("0"))) return PhoneNumberError.MustStartWith0or359
    if (value.startsWith("359") && value.substring(3).length != 9) return PhoneNumberError.Invalid
    if (value.startsWith("0") && value.substring(1).length != 9) return PhoneNumberError.Invalid
    return null
}