package io.github.hristogochev.template.data.mapper.local


import io.github.hristogochev.template.CityLocal
import io.github.hristogochev.template.domain.entity.dto.City
import io.github.hristogochev.template.domain.entity.dto.Region

fun City.toLocal(): CityLocal {
    return CityLocal(
        id = id,
        name = name,
        regionId = region.id,
    )
}

fun CityLocal.toModel(region: Region): City {
    return City(
        id,
        name,
        region
    )
}

