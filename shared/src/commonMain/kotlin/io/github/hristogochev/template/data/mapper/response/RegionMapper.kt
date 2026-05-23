package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.RegionEntity
import io.github.hristogochev.template.domain.entity.dto.Region

fun RegionEntity.toRegion(): Region {

    val name = name.trim()
    val country = country.toCountry()

    return Region(id, name, country)
}

