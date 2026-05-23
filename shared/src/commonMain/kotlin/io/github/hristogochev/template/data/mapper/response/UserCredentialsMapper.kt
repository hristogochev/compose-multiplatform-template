package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.UserCredentialsEntity
import io.github.hristogochev.template.domain.entity.auth.UserCredentials

fun UserCredentialsEntity.toUserCredentials() = UserCredentials(
    id = id,
    phoneNumber = phoneNumber,
    token = token
)

fun UserCredentials.toUserLocalEntity() = UserCredentialsEntity(
    id = id,
    phoneNumber = phoneNumber,
    token = token
)