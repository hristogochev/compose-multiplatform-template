package io.github.hristogochev.template.data.adapter.primitive

import app.cash.sqldelight.ColumnAdapter

object ULongColumnAdapter : ColumnAdapter<ULong, String> {

    override fun decode(databaseValue: String): ULong = databaseValue.toULong()

    override fun encode(value: ULong): String = value.toString()
}