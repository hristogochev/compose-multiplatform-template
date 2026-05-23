package io.github.hristogochev.template.domain.entity.auth

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class UserCredentials(
    val id: ULong,
    val phoneNumber: String,
    val token: String,
) : Parcelable
