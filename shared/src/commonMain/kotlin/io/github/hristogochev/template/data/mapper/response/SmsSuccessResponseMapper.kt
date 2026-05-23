package io.github.hristogochev.template.data.mapper.response


import io.github.hristogochev.template.data.entity.response.SmsSuccessResponseEntity
import io.github.hristogochev.template.data.entity.response.SmsSuccessResponseEntityResult
import io.github.hristogochev.template.domain.entity.response.SmsSuccessResponse
import io.github.hristogochev.template.domain.entity.response.SmsSuccessResponseResult

fun SmsSuccessResponseEntity.toSmsSuccessResponse() = SmsSuccessResponse(
    result = result.toSmsSuccessResponseResult()
)

fun SmsSuccessResponseEntityResult.toSmsSuccessResponseResult() = SmsSuccessResponseResult(
    success = success,
    autoFill = autoFill
)