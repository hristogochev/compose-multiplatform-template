package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.TokenResponseEntity
import io.github.hristogochev.template.domain.entity.response.TokenResponse


fun TokenResponseEntity.toTokenResponse() = TokenResponse(
    userId = userId,
    phoneNumber = phoneNumber,
    token = token
)

