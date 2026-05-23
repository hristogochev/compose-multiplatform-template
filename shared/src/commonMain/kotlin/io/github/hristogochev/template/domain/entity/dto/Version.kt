package io.github.hristogochev.template.domain.entity.dto

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class Version(
    val apiVersionCode: ULong,
    val appVersionCode: ULong,
    val iOSAppId: String
) : Parcelable

