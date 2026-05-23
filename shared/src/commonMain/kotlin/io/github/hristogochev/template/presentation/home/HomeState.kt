package io.github.hristogochev.template.presentation.home

import io.github.hristogochev.template.domain.entity.auth.UserCredentials
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class HomeState(
    val credentials: UserCredentials,
) : Parcelable


