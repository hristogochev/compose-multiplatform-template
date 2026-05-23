package io.github.hristogochev.template.data.adapter.table

import io.github.hristogochev.template.data.adapter.primitive.UIntColumnAdapter
import io.github.hristogochev.template.data.adapter.primitive.ULongColumnAdapter
import io.github.hristogochev.template.CityLocal

val CityLocalAdapter = CityLocal.Adapter(
    idAdapter = ULongColumnAdapter,
    regionIdAdapter = UIntColumnAdapter,
)