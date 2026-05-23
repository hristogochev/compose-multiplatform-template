package io.github.hristogochev.template.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseEntity(
    @SerialName("user_id")
    val userId: ULong,
    @SerialName("user_phone_number")
    val phoneNumber: String,
    @SerialName("token")
    val token: String
)

