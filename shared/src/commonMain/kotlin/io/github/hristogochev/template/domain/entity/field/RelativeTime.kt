package io.github.hristogochev.template.domain.entity.field


sealed interface RelativeTime {
    val dateTime: DateTime

    data class Today(override val dateTime: DateTime) : RelativeTime
    data class Yesterday(override val dateTime: DateTime) : RelativeTime
    data class Other(override val dateTime: DateTime) : RelativeTime
}