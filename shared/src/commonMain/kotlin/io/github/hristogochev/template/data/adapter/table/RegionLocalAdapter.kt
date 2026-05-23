package io.github.hristogochev.template.data.adapter.table

import io.github.hristogochev.template.data.adapter.primitive.UIntColumnAdapter
import io.github.hristogochev.template.RegionLocal

val RegionLocalAdapter = RegionLocal.Adapter(
    idAdapter = UIntColumnAdapter,
    countryIdAdapter = UIntColumnAdapter
)