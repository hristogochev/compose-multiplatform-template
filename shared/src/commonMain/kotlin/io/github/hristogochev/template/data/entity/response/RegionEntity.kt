package io.github.hristogochev.template.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionEntity(
    @SerialName("id")
    val id: UInt,
    @SerialName("name")
    val name: String,
    @SerialName("country")
    val country: CountryEntity,
)