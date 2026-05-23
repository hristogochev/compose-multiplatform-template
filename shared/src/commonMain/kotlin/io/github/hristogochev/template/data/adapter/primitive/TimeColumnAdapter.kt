package io.github.hristogochev.template.data.adapter.primitive

import app.cash.sqldelight.ColumnAdapter
import io.github.hristogochev.template.domain.entity.field.Time
import io.github.hristogochev.template.util.toTime
import kotlinx.datetime.LocalTime

object TimeColumnAdapter : ColumnAdapter<Time, String> {

    override fun decode(databaseValue: String): Time = LocalTime.parse(databaseValue).toTime()

    override fun encode(value: Time): String = value.toString()
}