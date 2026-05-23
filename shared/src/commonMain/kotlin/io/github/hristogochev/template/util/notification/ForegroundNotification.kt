package io.github.hristogochev.template.util.notification

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class ForegroundNotification(
    val content: NotificationContent,
    val data: Map<String, String>,
) : Parcelable
