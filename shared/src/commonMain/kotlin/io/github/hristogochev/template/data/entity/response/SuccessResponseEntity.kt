package io.github.hristogochev.template.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponseEntity(
    @SerialName("result")
    val result: SuccessResponseEntityResult
)

@Serializable
data class SuccessResponseEntityResult(
    @SerialName("success")
    val success: Boolean
)