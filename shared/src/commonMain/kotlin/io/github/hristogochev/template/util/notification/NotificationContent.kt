package io.github.hristogochev.template.util.notification

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class NotificationContent(val title: String, val body: String) : Parcelable




