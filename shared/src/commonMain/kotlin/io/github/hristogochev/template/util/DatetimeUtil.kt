package io.github.hristogochev.template.util


import io.github.hristogochev.template.domain.entity.field.Date
import io.github.hristogochev.template.domain.entity.field.DateTime
import io.github.hristogochev.template.domain.entity.field.RelativeTime
import io.github.hristogochev.template.domain.entity.field.Time
import io.github.hristogochev.template.domain.entity.field.toMilliseconds
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.offsetAt
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.time.Clock
import kotlin.time.Instant

fun defaultTimeZone(): TimeZone {
    return TimeZone.currentSystemDefault()
//    return TimeZone.of("Europe/Sofia")
}

//var time = LocalDateTime.parse("2023-11-17T11:59:50").toInstant(defaultTimeZone())
//fun increaseTimeByOneHour() {
//    time = time.plus(1, DateTimeUnit.HOUR, defaultTimeZone())
//}

//fun increaseTimeByTenSeconds() {
//    time = time.plus(10, DateTimeUnit.SECOND, defaultTimeZone())
//}
//fun timeNow(): Instant = time

//fun timeNow(): Instant = LocalDateTime.parse("2024-12-20T22:00:00").toInstant(defaultTimeZone())
fun timeNow(): Instant = Clock.System.now()

fun fromTodayToDatesAll(maxDays: UInt, timeZone: TimeZone): List<DateTime> {
    return timeNow().toDateTime(timeZone).toDatesAll(maxDays)
}

fun Instant.minusDays(days: Int, timeZone: TimeZone): Instant =
    minus(days, DateTimeUnit.DAY, timeZone)

fun Instant.plusDays(days: Int, timeZone: TimeZone): Instant =
    plus(days, DateTimeUnit.DAY, timeZone)

fun Instant.plusDays(days: UInt, timeZone: TimeZone): Instant =
    plus(days.toInt(), DateTimeUnit.DAY, timeZone)

fun Instant.toLocalDateTime(timeZone: TimeZone) = toLocalDateTime(timeZone)

fun DateTime.formatWithDots(): String {
    val monthStr = stringFormat("%02d", monthNumber)
    val dayStr = stringFormat("%02d", dayOfMonth)

    return "$dayStr.$monthStr.${year}"
}

fun DateTime.toTimeZoneInfallible(timeZone: TimeZone): DateTime {
    return this.epochMilliseconds.epochMillisecondsToDateTimeInfallible(timeZone)
}

fun DateTime.toRelativeTime(timeZone: TimeZone): RelativeTime {
    val todayInstant = timeNow()

    val today = todayInstant.toDateTime(timeZone = timeZone)

    if (today.year == this.year &&
        today.monthNumber == this.monthNumber &&
        today.dayOfMonth == this.dayOfMonth
    ) {
        return RelativeTime.Today(this)
    }

    val yesterday =
        todayInstant.minusDays(1, timeZone).toDateTime(timeZone)

    if (yesterday.year == this.year &&
        yesterday.monthNumber == this.monthNumber &&
        yesterday.dayOfMonth == this.dayOfMonth
    ) {
        return RelativeTime.Yesterday(this)
    }

    return RelativeTime.Other(this)
}

fun String.toTimeZoneFallible(): TimeZone? {
    return runCatching {
        TimeZone.of(this)
    }.getOrNull()
}

fun DateTime.time(): Time {
    return Time(hour, minute, second, millis)
}

fun DateTime.date(): Date {
    return Date(year, monthNumber, dayOfWeek, dayOfMonth)
}

fun DateTime.compareDate(other: DateTime): Boolean {
    if (this === other) return true

    if (year != other.year) return false
    if (monthNumber != other.monthNumber) return false
    if (dayOfWeek != other.dayOfWeek) return false
    if (dayOfMonth != other.dayOfMonth) return false

    return true
}

fun String.toTimeZoneInfallible(): TimeZone {
    return TimeZone.of(this)
}


fun TimeZone.asUtcOffsetString(): String {
    val timeZone = this

    val offset = timeZone.offsetAt(timeNow())
    val hours = offset.totalSeconds / 3600
    val minutes = abs(offset.totalSeconds % 3600) / 60

    return "UTC${if (hours >= 0) "+" else ""}$hours:${if (minutes < 10) "0" else ""}$minutes"
}


fun LocalTime.toTime(): Time {
    val millis = nanosecond / 1_000_000
    return Time(
        hour = hour,
        minute = minute,
        second = second,
        millis = millis
    )
}

fun LocalDate.toDate(): Date {
    return Date(
        year = year,
        monthNumber = month.number,
        dayOfWeek = dayOfWeek,
        dayOfMonth = day,
    )
}

fun Instant.toDateTime(timeZone: TimeZone): DateTime {
    val localDateTime = toLocalDateTime(timeZone)
    val millis = localDateTime.nanosecond / 1_000_000
    return DateTime(
        epochMilliseconds = this.toEpochMilliseconds(),
        year = localDateTime.year,
        monthNumber = localDateTime.month.number,
        dayOfWeek = localDateTime.dayOfWeek,
        dayOfMonth = localDateTime.day,
        hour = localDateTime.hour,
        minute = localDateTime.minute,
        second = localDateTime.second,
        millis = millis
    )
}

fun Instant.noon(timeZone: TimeZone): Instant {
    val currentLocalTime = toLocalDateTime(timeZone)
    val day = currentLocalTime.day.format2Letter()
    val month = currentLocalTime.month.number.format2Letter()
    val year = currentLocalTime.year
    val noon = "${year}-${month}-${day}T12:00:00"
    return LocalDateTime.parse(noon).toInstant(timeZone)
}

fun DateTime.plusDays(days: UInt, timeZone: TimeZone): DateTime {
    val instant = Instant.fromEpochMilliseconds(epochMilliseconds).plusDays(days.toInt(), timeZone)
    return instant.toDateTime(timeZone)
}

fun DateTime.minusDays(days: UInt, timeZone: TimeZone): DateTime {
    val instant = Instant.fromEpochMilliseconds(epochMilliseconds).minusDays(days.toInt(), timeZone)
    return instant.toDateTime(timeZone)
}

fun DateTime.minusMonths(months: UInt, timeZone: TimeZone): DateTime {
    val instant = Instant.fromEpochMilliseconds(epochMilliseconds)
        .minus(months.toInt(), DateTimeUnit.MONTH, timeZone)
    return instant.toDateTime(timeZone)
}

fun DateTime.minusYears(years: UInt, timeZone: TimeZone): DateTime {
    val instant = Instant.fromEpochMilliseconds(epochMilliseconds)
        .minus(years.toInt(), DateTimeUnit.YEAR, timeZone)
    return instant.toDateTime(timeZone)
}


fun Time.toDateTimePeriod(): DateTimePeriod {
    return DateTimePeriod(0, 0, 0, hour, minute, second, 0)
}

fun DateTime.plusTime(time: Time, timeZone: TimeZone): DateTime {
    val newEpoch = epochMilliseconds + time.toMilliseconds()
    val instant = Instant.fromEpochMilliseconds(newEpoch)
    return instant.toDateTime(timeZone)
}


fun Date.toDateTime(timeZone: TimeZone): DateTime {
    val dateTimeString = toDateTimeString()
    return LocalDateTime.parse(dateTimeString).toInstant(timeZone).toDateTime(timeZone)
}

fun DateTime.startOfDay(): DateTime {
    val time = time()
    val timeMilliseconds = time.toMilliseconds()
    val newEpochMilliseconds = epochMilliseconds - timeMilliseconds
    return copy(
        epochMilliseconds = newEpochMilliseconds,
        hour = 0,
        minute = 0,
        second = 0,
        millis = 0
    )
}

fun DateTime.endOfDay(): DateTime {
    val timeMilliseconds = time().toMilliseconds()

    val endOfDayMilliseconds = Time(23, 59, 59, 0).toMilliseconds()

    val newEpochMilliseconds = epochMilliseconds - timeMilliseconds + endOfDayMilliseconds

    return copy(
        epochMilliseconds = newEpochMilliseconds,
        hour = 23,
        minute = 59,
        second = 59,
        millis = 0
    )
}

fun Instant.startOfDay(timeZone: TimeZone): Instant {
    val currentLocalTime = toLocalDateTime(timeZone)
    val day = currentLocalTime.day.format2Letter()
    val month = currentLocalTime.month.number.format2Letter()
    val year = currentLocalTime.year
    val date = "${year}-${month}-${day}T00:00:00"
    return LocalDateTime.parse(date).toInstant(timeZone)
}

fun Instant.endOfDay(timeZone: TimeZone): Instant {
    val currentLocalTime = toLocalDateTime(timeZone)
    val day = currentLocalTime.day.format2Letter()
    val month = currentLocalTime.month.number.format2Letter()
    val year = currentLocalTime.year
    val date = "${year}-${month}-${day}T23:59:59"
    return LocalDateTime.parse(date).toInstant(timeZone)
}


fun Instant.mondayOfWeek(): Instant {
    val timeZone = defaultTimeZone()
    val nowDate = toLocalDateTime(timeZone)
    return when (nowDate.dayOfWeek) {
        DayOfWeek.MONDAY -> this
        DayOfWeek.TUESDAY -> minusDays(1, timeZone)
        DayOfWeek.WEDNESDAY -> minusDays(2, timeZone)
        DayOfWeek.THURSDAY -> minusDays(3, timeZone)
        DayOfWeek.FRIDAY -> minusDays(4, timeZone)
        DayOfWeek.SATURDAY -> minusDays(5, timeZone)
        DayOfWeek.SUNDAY -> minusDays(6, timeZone)
    }
}

fun Instant.toDatesAll(
    maxDays: Int,
): List<Instant> {
    val timeZone = defaultTimeZone()

    return (0..<maxDays).map {
        plus(it, DateTimeUnit.DAY, timeZone)
    }
}

fun DateTime.replaceTime(time: Time): DateTime {
    val epoch = this.startOfDay().epochMilliseconds + time.toMilliseconds()
    return this.copy(
        epochMilliseconds = epoch,
        hour = time.hour,
        minute = time.minute,
        second = time.second,
        millis = time.millis
    )
}


fun DateTime.toDatesAll(
    maxDays: UInt,
    inclusiveStart: Boolean = true,
): List<DateTime> {
    val instant = Instant.fromEpochMilliseconds(epochMilliseconds)

    val timeZone = defaultTimeZone()

    val start = if (inclusiveStart) 0u else 1u
    return (start..<maxDays).map {
        instant.plus(it.toInt(), DateTimeUnit.DAY, timeZone)
    }.map {
        it.toDateTime(timeZone)
    }
}


fun Instant.Companion.fromEpochMillisecondsOrNull(epochMilliseconds: Long): Instant? {
    return runCatching {
        fromEpochMilliseconds(epochMilliseconds)
    }.getOrNull()
}

fun Long.epochMillisecondsToDateTime(timeZone: TimeZone): DateTime? {
    return runCatching {
        val dateTimeInstant = Instant.fromEpochMilliseconds(this)
        dateTimeInstant.toDateTime(timeZone = timeZone)
    }.getOrNull()
}

fun Long.epochMillisecondsToDateTimeInfallible(timeZone: TimeZone): DateTime {
    val dateTimeInstant = Instant.fromEpochMilliseconds(this)
    return dateTimeInstant.toDateTime(timeZone = timeZone)
}
