package io.github.hristogochev.template.util.notification

data class NotificationDataOptional(val data: Map<String, String>?)

fun Map<String, String>?.toOptional() = NotificationDataOptional(this)