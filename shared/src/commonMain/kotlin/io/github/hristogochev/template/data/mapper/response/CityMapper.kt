package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.CityEntity
import io.github.hristogochev.template.domain.entity.dto.City

fun CityEntity.toCity(): City {

    val name = name.trim()
    val region = region.toRegion()

    return City(id, name, region)
}

