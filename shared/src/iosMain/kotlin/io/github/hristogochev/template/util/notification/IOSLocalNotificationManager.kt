package io.github.hristogochev.template.util.notification

import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.random.Random

class IOSLocalNotificationManager : LocalNotificationManager {
    override fun notify(
        title: String,
        body: String,
        payloadData: Map<String, String>,
    ): Int {
        val notificationId = Random.nextInt(0, Int.MAX_VALUE)

        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

        val notificationContent = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(body)
            setSound(UNNotificationSound.defaultSound)
            setUserInfo(userInfo + payloadData)
        }

        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(1.0, false)

        val notificationRequest = UNNotificationRequest.requestWithIdentifier(
            identifier = notificationId.toString(),
            content = notificationContent,
            trigger = trigger
        )

        notificationCenter.addNotificationRequest(notificationRequest) { error ->
            error?.also { println("Error showing notification: $error") }
        }

        return notificationId
    }

    override fun remove(id: Int) {
        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

        notificationCenter.removeDeliveredNotificationsWithIdentifiers(listOf(id.toString()))
    }

    override fun removeAll() {
        val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

        notificationCenter.removeAllDeliveredNotifications()
    }
}