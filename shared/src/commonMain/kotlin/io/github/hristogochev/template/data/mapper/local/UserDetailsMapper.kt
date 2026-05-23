package io.github.hristogochev.template.data.mapper.local

import io.github.hristogochev.template.UserDetailsLocal
import io.github.hristogochev.template.domain.entity.auth.UserDetails

fun UserDetails.toLocal(): UserDetailsLocal {
    return UserDetailsLocal(
        id,
        phoneNumber,
        firstName,
        lastName,
        notificationsEnabled
    )
}

fun UserDetailsLocal.toModel(): UserDetails {
    return UserDetails(
        id,
        phoneNumber,
        firstName,
        lastName,
        notificationsEnabled
    )
}

