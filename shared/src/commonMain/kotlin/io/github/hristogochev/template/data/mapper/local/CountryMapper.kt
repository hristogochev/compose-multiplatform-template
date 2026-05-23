package io.github.hristogochev.template.data.mapper.local

import io.github.hristogochev.template.CountryLocal
import io.github.hristogochev.template.domain.entity.dto.Country

fun Country.toLocal(): CountryLocal {
    return CountryLocal(
        id = id,
        name = name,
    )
}

fun CountryLocal.toModel(): Country {
    return Country(
        id,
        name,
    )
}

