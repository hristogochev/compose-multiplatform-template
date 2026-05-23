package io.github.hristogochev.template.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisteredResponseEntity(
    @SerialName("registered")
    val registered: Boolean
)

