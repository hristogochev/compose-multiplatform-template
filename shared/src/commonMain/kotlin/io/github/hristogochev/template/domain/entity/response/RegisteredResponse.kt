package io.github.hristogochev.template.domain.entity.response

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class RegisteredResponse(
    val registered: Boolean,
) : Parcelable