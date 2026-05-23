package io.github.hristogochev.template.data.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginSMSRequestEntity(
    @SerialName("phone_number")
    val phoneNumber: String,
)
