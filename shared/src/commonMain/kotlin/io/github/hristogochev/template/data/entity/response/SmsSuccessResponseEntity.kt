package io.github.hristogochev.template.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsSuccessResponseEntity(
    @SerialName("result")
    val result: SmsSuccessResponseEntityResult,
)

@Serializable
data class SmsSuccessResponseEntityResult(
    @SerialName("success")
    val success: Boolean,
    @SerialName("autofill")
    val autoFill: Boolean = false,
)