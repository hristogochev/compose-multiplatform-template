package io.github.hristogochev.template.domain.entity.response

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class TokenResponse(
    val userId: ULong,
    val phoneNumber: String,
    val token: String,
) : Parcelable

