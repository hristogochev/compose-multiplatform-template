package io.github.hristogochev.template.data.adapter.primitive

import app.cash.sqldelight.ColumnAdapter
import io.github.hristogochev.template.domain.entity.field.Date
import io.github.hristogochev.template.util.toDate
import kotlinx.datetime.LocalDate

object DateColumnAdapter : ColumnAdapter<Date, String> {

    override fun decode(databaseValue: String): Date = LocalDate.parse(databaseValue).toDate()

    override fun encode(value: Date): String = value.toString()
}