package io.github.hristogochev.template.data.adapter.table

import io.github.hristogochev.template.CountryLocal
import io.github.hristogochev.template.data.adapter.primitive.UIntColumnAdapter

val CountryLocalAdapter = CountryLocal.Adapter(
    idAdapter = UIntColumnAdapter,
)