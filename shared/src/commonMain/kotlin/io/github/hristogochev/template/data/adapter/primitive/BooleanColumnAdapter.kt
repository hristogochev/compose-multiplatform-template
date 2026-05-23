package io.github.hristogochev.template.data.adapter.primitive

import app.cash.sqldelight.ColumnAdapter

object BooleanColumnAdapter : ColumnAdapter<Boolean, Long> {

    override fun decode(databaseValue: Long): Boolean = databaseValue == 1L

    override fun encode(value: Boolean): Long = if (value) 1L else 0L
}