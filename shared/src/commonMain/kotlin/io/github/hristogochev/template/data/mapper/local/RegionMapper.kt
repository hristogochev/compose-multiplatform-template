package io.github.hristogochev.template.data.mapper.local

import io.github.hristogochev.template.RegionLocal
import io.github.hristogochev.template.domain.entity.dto.Country
import io.github.hristogochev.template.domain.entity.dto.Region

fun Region.toLocal(): RegionLocal {
    return RegionLocal(
        id = id,
        name = name,
        countryId = country.id
    )
}

fun RegionLocal.toModel(country: Country): Region {
    return Region(
        id,
        name,
        country
    )
}

