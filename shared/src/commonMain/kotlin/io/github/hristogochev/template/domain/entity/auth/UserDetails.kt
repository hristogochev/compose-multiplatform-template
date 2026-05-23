package io.github.hristogochev.template.domain.entity.auth

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class UserDetails(
    val id: ULong,
    val phoneNumber: String,
    val firstName: String?,
    val lastName: String?,
    val notificationsEnabled: Boolean,
) : Parcelable