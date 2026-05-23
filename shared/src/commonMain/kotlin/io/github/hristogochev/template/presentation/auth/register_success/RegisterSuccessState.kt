package io.github.hristogochev.template.presentation.auth.register_success

import io.github.hristogochev.template.domain.entity.auth.UserCredentials
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class RegisterSuccessState(
    val credentials: UserCredentials,
) : Parcelable

