package io.github.hristogochev.template.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityEntity(
    @SerialName("id")
    val id: ULong,
    @SerialName("name")
    val name: String,
    @SerialName("region")
    val region: RegionEntity,
)
