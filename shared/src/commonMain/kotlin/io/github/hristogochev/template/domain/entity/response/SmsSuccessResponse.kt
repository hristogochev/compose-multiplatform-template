package io.github.hristogochev.template.domain.entity.response

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class SmsSuccessResponse(
    val result: SmsSuccessResponseResult,
) : Parcelable

@Parcelize
data class SmsSuccessResponseResult(
    val success: Boolean,
    val autoFill: Boolean,
) : Parcelable