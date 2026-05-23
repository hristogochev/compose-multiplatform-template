package io.github.hristogochev.template.data.mapper.request

import io.github.hristogochev.template.data.entity.request.UpdateNotificationsEnabledRequestEntity
import io.github.hristogochev.template.domain.entity.request.UpdateNotificationsEnabledRequest

fun UpdateNotificationsEnabledRequest.toEntity(): UpdateNotificationsEnabledRequestEntity {
    return UpdateNotificationsEnabledRequestEntity(notificationsEnabled = notificationsEnabled)
}