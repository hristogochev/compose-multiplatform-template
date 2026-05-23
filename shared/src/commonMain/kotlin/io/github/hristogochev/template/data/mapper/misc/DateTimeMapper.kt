package io.github.hristogochev.template.data.mapper.misc

import io.github.hristogochev.template.domain.entity.field.DateTime
import io.github.hristogochev.template.domain.entity.field.Time
import io.github.hristogochev.template.domain.entity.field.TimePair
import io.github.hristogochev.template.util.format2Letter
import io.github.hristogochev.template.util.toDate
import io.github.hristogochev.template.util.toDateTime
import io.github.hristogochev.template.util.toTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Instant


internal fun String.fromUtcToDateTime(timeZone: TimeZone) = runCatching {
    val normalized = this.replace(' ', 'T')
    val timeRemote = LocalDateTime.parse(normalized).toInstant(TimeZone.UTC)
    timeRemote.toDateTime(timeZone)
}

internal fun String.toDate() = runCatching {
    val normalized = this.replace(' ', 'T')
    val date = LocalDate.parse(normalized)
    date.toDate()
}

internal fun String.toTime() = runCatching {
    val normalized = this.replace(' ', 'T')
    val timeRemote = LocalTime.parse(normalized)
    timeRemote.toTime()
}


internal fun List<String>.toTimes(): TimePair? {
    if (size != 2) return null

    val fromDateTime = getOrNull(0)?.toTime()?.getOrNull() ?: return null
    val toDateTime = getOrNull(1)?.toTime()?.getOrNull() ?: return null
    return TimePair(fromDateTime, toDateTime)
}

fun TimePair.toEntities(): List<String> {
    val first = first.toUtcString()
    val second = second.toUtcString()

    return listOf(first, second)
}

fun TimePair.toEntityPair(): Pair<String, String> {
    val first = first.toUtcString()
    val second = second.toUtcString()

    return Pair(first, second)
}

fun Long.fromLocalEpochToUtcDateTime(): DateTime? {
    return runCatching {
        val dateTimeInstant = Instant.fromEpochMilliseconds(this)
        dateTimeInstant.toDateTime(timeZone = TimeZone.UTC)
    }.getOrNull()
}

fun Time.toUtcString(): String {
    val hour = hour.format2Letter()
    val minute = minute.format2Letter()
    val second = second.format2Letter()
    val millis = millis
    return "${hour}:${minute}:${second}.$millis"
}


