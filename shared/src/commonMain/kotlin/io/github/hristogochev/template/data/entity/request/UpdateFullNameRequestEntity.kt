package io.github.hristogochev.template.data.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateFullNameRequestEntity(
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?
)
