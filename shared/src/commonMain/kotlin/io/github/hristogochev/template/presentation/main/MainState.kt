package io.github.hristogochev.template.presentation.main

import io.github.hristogochev.template.presentation.app.AppPrepared
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.notification.NotificationContent

@Parcelize
data class MainState(
    val prepared: AppPrepared.Prepared,
    val sessionExpired: Boolean = false,
    val needsUpdate: UpdateState? = null,
    val loggingOut: Boolean = false,
    val notificationDialogs: List<NotificationDialog> = emptyList(),
) : Parcelable


@Parcelize
data class UpdateState(val iOSAppId: String) : Parcelable

@Parcelize
data class NotificationDialog(
    val id: Int,
    val dialogTitle: String?,
    val dialogBody: String?,
    val dialogOnClickText: String?,
    val dialogOnDismissText: String?,
    val content: NotificationContent,
    val data: Map<String, String>,
) : Parcelable
