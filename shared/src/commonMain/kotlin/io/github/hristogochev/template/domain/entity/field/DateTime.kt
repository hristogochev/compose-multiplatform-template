package io.github.hristogochev.template.domain.entity.field

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.format2Letter
import io.github.hristogochev.template.util.timeNow
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetAt

@Parcelize
data class DateTime(
    val epochMilliseconds: Long,
    val year: Int,
    val monthNumber: Int,
    val dayOfWeek: DayOfWeek,
    val dayOfMonth: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val millis: Int,
) : Comparable<DateTime>, Parcelable {

    override fun compareTo(other: DateTime): Int {
        if (this.year > other.year) return 1
        if (this.year < other.year) return -1
        if (this.monthNumber > other.monthNumber) return 1
        if (this.monthNumber < other.monthNumber) return -1
        if (this.dayOfMonth > other.dayOfMonth) return 1
        if (this.dayOfMonth < other.dayOfMonth) return -1
        if (this.hour > other.hour) return 1
        if (this.hour < other.hour) return -1
        if (this.minute > other.minute) return 1
        if (this.minute < other.minute) return -1
        if (this.second > other.second) return 1
        if (this.second < other.second) return -1
        if (this.millis > other.millis) return 1
        if (this.millis < other.millis) return -1
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DateTime

        if (year != other.year) return false
        if (monthNumber != other.monthNumber) return false
        if (dayOfWeek != other.dayOfWeek) return false
        if (dayOfMonth != other.dayOfMonth) return false
        if (hour != other.hour) return false
        if (minute != other.minute) return false
        if (second != other.second) return false
        if (millis != other.millis) return false

        return true
    }

    override fun hashCode(): Int {
        var result = year
        result = 31 * result + monthNumber
        result = 31 * result + dayOfWeek.hashCode()
        result = 31 * result + dayOfMonth
        result = 31 * result + hour
        result = 31 * result + minute
        result = 31 * result + second
        result = 31 * result + millis
        return result
    }

    override fun toString(): String {
        val dateTime = this
        val year = dateTime.year
        val month = dateTime.monthNumber.format2Letter()
        val day = dateTime.dayOfMonth.format2Letter()
        val hour = dateTime.hour.format2Letter()
        val minute = dateTime.minute.format2Letter()
        val second = dateTime.second.format2Letter()
        val millis = dateTime.millis
        return "${year}-${month}-${day} ${hour}:${minute}:${second}.${millis}"
    }
}

fun TimeZone.difference(timeZone: TimeZone) {
    val offset = timeZone.offsetAt(timeNow()).totalSeconds
}

fun DateTime.toMilliseconds(): Long {
    val totalMillisFromHours = hour.toLong() * 60 * 60 * 1000
    val totalMillisFromMinutes = minute.toLong() * 60 * 1000
    val totalMillisFromSeconds = second.toLong() * 1000
    return totalMillisFromHours + totalMillisFromMinutes + totalMillisFromSeconds + millis
}
