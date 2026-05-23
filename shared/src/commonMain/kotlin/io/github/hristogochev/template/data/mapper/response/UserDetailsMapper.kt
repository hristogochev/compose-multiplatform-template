package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.UserDetailsEntity
import io.github.hristogochev.template.domain.entity.auth.UserDetails

fun UserDetailsEntity.toUserDetails() = UserDetails(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    notificationsEnabled = notificationsEnabled
)

fun UserDetails.toUserDetailsEntity() = UserDetailsEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    notificationsEnabled = notificationsEnabled
)

