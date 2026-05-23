package io.github.hristogochev.template.domain.entity.field

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.format2Letter
import kotlinx.datetime.DayOfWeek

@Parcelize
data class Date(
    val year: Int,
    val monthNumber: Int,
    val dayOfWeek: DayOfWeek,
    val dayOfMonth: Int,
) : Comparable<Date>, Parcelable {

    override fun compareTo(other: Date): Int {
        if (this.year > other.year) return 1
        if (this.year < other.year) return -1
        if (this.monthNumber > other.monthNumber) return 1
        if (this.monthNumber < other.monthNumber) return -1
        if (this.dayOfMonth > other.dayOfMonth) return 1
        if (this.dayOfMonth < other.dayOfMonth) return -1
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Date

        if (year != other.year) return false
        if (monthNumber != other.monthNumber) return false
        if (dayOfWeek != other.dayOfWeek) return false
        if (dayOfMonth != other.dayOfMonth) return false

        return true
    }

    override fun hashCode(): Int {
        var result = year
        result = 31 * result + monthNumber
        result = 31 * result + dayOfWeek.hashCode()
        result = 31 * result + dayOfMonth
        return result
    }

    override fun toString(): String {
        val year = year
        val month = monthNumber.format2Letter()
        val day = dayOfMonth.format2Letter()
        return "${year}-${month}-${day}"
    }

    fun toDateTimeString(): String {
        val year = year
        val month = monthNumber.format2Letter()
        val day = dayOfMonth.format2Letter()
        return "${year}-${month}-${day}T00:00:00.0"
    }
}
