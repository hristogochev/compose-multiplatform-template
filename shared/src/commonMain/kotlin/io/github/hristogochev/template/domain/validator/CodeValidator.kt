package io.github.hristogochev.template.domain.validator

import io.github.hristogochev.template.domain.error.CodeError

fun String.validateCode(): CodeError?{
    val value = this
    if (value.isEmpty()) return CodeError.Empty
    if (value.length != 4) return CodeError.MustBe4Letters
    return null
}