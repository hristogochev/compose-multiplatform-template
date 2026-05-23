package io.github.hristogochev.template.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCredentialsEntity(
    @SerialName("id")
    val id: ULong,
    @SerialName("phone_number")
    val phoneNumber: String,
    @SerialName("token")
    val token: String
)
