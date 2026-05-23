package io.github.hristogochev.template.domain.entity.field

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize


@Parcelize
data class TimePair(val first: Time, val second: Time) : Parcelable
