package io.github.hristogochev.template.data.adapter.table

import io.github.hristogochev.template.UserDetailsLocal
import io.github.hristogochev.template.data.adapter.primitive.ULongColumnAdapter

val UserDetailsLocalAdapter = UserDetailsLocal.Adapter(
    idAdapter = ULongColumnAdapter,
)