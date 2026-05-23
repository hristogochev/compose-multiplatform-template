package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.CountryEntity
import io.github.hristogochev.template.domain.entity.dto.Country

fun CountryEntity.toCountry(): Country {

    val name = name.trim()

    return Country(id, name)
}

