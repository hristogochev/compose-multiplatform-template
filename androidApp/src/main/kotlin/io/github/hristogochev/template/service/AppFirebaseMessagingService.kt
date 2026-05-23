package io.github.hristogochev.template.service

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.github.hristogochev.template.domain.validator.validateDeviceToken
import io.github.hristogochev.template.util.notification.LocalNotificationManager
import io.github.hristogochev.template.util.notification.PushNotificationManager
import io.github.hristogochev.template.util.filterNotNullKeys
import io.github.hristogochev.template.util.filterNotNullValues
import io.github.hristogochev.template.util.notification.EventNotificationManager
import io.github.hristogochev.template.util.notification.ForegroundNotification
import io.github.hristogochev.template.util.notification.NotificationContent

class AppFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val deviceToken = token
            .takeIf { deviceToken -> deviceToken.validateDeviceToken() == null }

        PushNotificationManager.shared.updateDeviceToken(deviceToken)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val eventNotificationManager = EventNotificationManager.shared

        val notification = message.notification
        val payloadData = message.data.filterNotNullKeys().filterNotNullValues()

        val (notificationTitle, notificationBody) = notification?.let {
            val title = it.title
            val body = it.body
            if (title == null || body == null) {
                return
            }
            title to body
        } ?: run {
            if (payloadData.isEmpty()) {
                return
            }

            val isDataForNotification = payloadData["show_notification"] != null
            if (!isDataForNotification) {
                return
            }

            val notificationTitle = payloadData["notification_title"]
            val notificationBody = payloadData["notification_body"]
            if (notificationTitle == null || notificationBody == null) {
                return
            }
            notificationTitle to notificationBody
        }

        if (isAppInForeground()) {
            val notificationContent =
                NotificationContent(notificationTitle, notificationBody)

            eventNotificationManager.notificationReceivedInForeground(
                ForegroundNotification(notificationContent, payloadData)
            )
            return
        }

        LocalNotificationManager.shared.notify(
            title = notificationTitle,
            body = notificationBody,
            payloadData = payloadData
        )
    }

    private fun isAppInForeground(): Boolean {
        return ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
    }
}