package io.github.hristogochev.template.domain.entity.request

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class RegisterValidateRequest(
    val phoneNumber: String,
    val code: String,
) : Parcelable
