package io.github.hristogochev.template.data.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNotificationsEnabledRequestEntity(
    @SerialName("notifications_enabled")
    val notificationsEnabled: Boolean
)
