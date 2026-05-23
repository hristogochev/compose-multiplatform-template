package io.github.hristogochev.template.domain.entity.field

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.format2Letter

@Parcelize
data class Time(
    val hour: Int,
    val minute: Int,
    val second: Int,
    val millis: Int,
) : Comparable<Time>, Parcelable {

    override fun compareTo(other: Time): Int {
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

        other as Time

        if (hour != other.hour) return false
        if (minute != other.minute) return false
        if (second != other.second) return false
        if (millis != other.millis) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hour
        result = 31 * result + minute
        result = 31 * result + second
        result = 31 * result + millis
        return result
    }

    override fun toString(): String {
        val dateTime = this
        val hour = dateTime.hour.format2Letter()
        val minute = dateTime.minute.format2Letter()
        val second = dateTime.second.format2Letter()
        val millis = dateTime.millis
        return "${hour}:${minute}:${second}.${millis}"
    }

    companion object {
        fun startOfDay(): Time = Time(0, 0, 0, 0)
        fun endOfDay(): Time = Time(23, 59, 59, 0)
    }
}

fun Time.toMilliseconds(): Long {
    val totalSeconds = (hour * 3600) + (minute * 60) + second
    return totalSeconds * 1000L + millis
}


