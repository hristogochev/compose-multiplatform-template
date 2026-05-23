package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.SuccessResponseEntity
import io.github.hristogochev.template.data.entity.response.SuccessResponseEntityResult
import io.github.hristogochev.template.domain.entity.response.SuccessResponse
import io.github.hristogochev.template.domain.entity.response.SuccessResponseResult

fun SuccessResponseEntity.toSuccessResponse() = SuccessResponse(
    result = result.toSuccessResponseResult()
)

fun SuccessResponseEntityResult.toSuccessResponseResult() = SuccessResponseResult(
    success = success
)