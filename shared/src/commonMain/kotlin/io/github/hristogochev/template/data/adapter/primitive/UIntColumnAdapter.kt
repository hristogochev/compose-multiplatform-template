package io.github.hristogochev.template.data.adapter.primitive

import app.cash.sqldelight.ColumnAdapter

object UIntColumnAdapter : ColumnAdapter<UInt, String> {

    override fun decode(databaseValue: String): UInt = databaseValue.toUInt()

    override fun encode(value: UInt): String = value.toString()
}