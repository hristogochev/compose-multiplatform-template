package io.github.hristogochev.template.util.notification

import cocoapods.FirebaseMessaging.FIRMessaging
import cocoapods.FirebaseMessaging.FIRMessagingDelegateProtocol
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationState
import platform.UIKit.registerForRemoteNotifications
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNNotification
import platform.UserNotifications.UNNotificationPresentationOptions
import platform.UserNotifications.UNNotificationResponse
import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNUserNotificationCenterDelegateProtocol
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.experimental.ExperimentalObjCName

val NOTIFICATION_PERMISSIONS = UNAuthorizationOptionAlert or
        UNAuthorizationOptionSound or
        UNAuthorizationOptionBadge


@Suppress("unused")
@OptIn(ExperimentalForeignApi::class, ExperimentalObjCName::class)
@ObjCName("IOSPushNotificationManager")
class IOSPushNotificationManager : PushNotificationManager {

    private val _deviceToken = MutableStateFlow<String?>(null)

    override val deviceToken = _deviceToken.asStateFlow()

    override fun updateDeviceToken(deviceToken: String?) {
        _deviceToken.update { deviceToken }
    }

    init {
        FIRMessaging.messaging().delegate = FirebaseMessageDelegate {
            updateDeviceToken(it)
        }
        UNUserNotificationCenter.currentNotificationCenter().delegate = NotificationDelegate()
        UIApplication.sharedApplication.registerForRemoteNotifications()
    }

    override suspend fun getToken(): String? = suspendCoroutine { cont ->
        FIRMessaging.messaging().tokenWithCompletion { token, error ->
            error?.also {
                println("Error while getting token: $it")
            }
            cont.resume(token)
        }
    }

    override suspend fun deleteMyToken() = suspendCoroutine { cont ->
        FIRMessaging.messaging().deleteTokenWithCompletion { error ->
            error?.also {
                println("Error deleting token: $it")
            }
            cont.resume(error == null)
        }
    }

    override suspend fun subscribeToTopic(topic: String) {
        FIRMessaging.messaging().subscribeToTopic(topic)
    }

    override suspend fun unsubscribeFromTopic(topic: String) {
        FIRMessaging.messaging().unsubscribeFromTopic(topic)
    }

    /* IOS specific */
    private val _apnsTokenSet = MutableStateFlow(false)

    override val apnsTokenSet: StateFlow<Boolean> = _apnsTokenSet.asStateFlow()

    override fun setApnsToken() {
        _apnsTokenSet.update { true }
    }

    override fun onApplicationDidReceiveRemoteNotification(userInfo: Map<Any?, *>): Boolean {
        val eventNotificationManager = EventNotificationManager.shared

        val payloadData = userInfo.asPayloadData()

        if (payloadData.isEmpty()) {
            return false
        }

        val isDataForNotification = payloadData["show_notification"] != null

        if (!isDataForNotification) {
            return false
        }

        val notificationTitle = payloadData["notification_title"]
        val notificationBody = payloadData["notification_body"]

        if (notificationTitle == null || notificationBody == null) {
            return false
        }


        val notificationContent = NotificationContent(notificationTitle, notificationBody)

        if (!isAppInForeground()) {
            LocalNotificationManager.shared.notify(
                notificationTitle,
                notificationBody,
                payloadData
            )
            return true
        }

        eventNotificationManager.notificationReceivedInForeground(
            ForegroundNotification(notificationContent, payloadData)
        )

        return true
    }

    private class FirebaseMessageDelegate(private val onNewToken: (String) -> Unit) :
        FIRMessagingDelegateProtocol, NSObject() {
        override fun messaging(messaging: FIRMessaging, didReceiveRegistrationToken: String?) {
            didReceiveRegistrationToken?.let { token ->
                onNewToken(token)
            }
        }
    }

    private class NotificationDelegate : UNUserNotificationCenterDelegateProtocol, NSObject() {
        override fun userNotificationCenter(
            center: UNUserNotificationCenter,
            didReceiveNotificationResponse: UNNotificationResponse,
            withCompletionHandler: () -> Unit,
        ) {

            val eventNotificationManager = EventNotificationManager.shared

            val userInfo = didReceiveNotificationResponse.notification.request.content.userInfo

            val payloadData = userInfo.asPayloadData()

            eventNotificationManager.notificationClicked(payloadData)
        }

        override fun userNotificationCenter(
            center: UNUserNotificationCenter,
            willPresentNotification: UNNotification,
            withCompletionHandler: (UNNotificationPresentationOptions) -> Unit,
        ) {
            return withCompletionHandler(NOTIFICATION_PERMISSIONS)
        }
    }

    private fun isAppInForeground(): Boolean {
        return when (UIApplication.sharedApplication.applicationState) {
            UIApplicationState.UIApplicationStateBackground -> false
            UIApplicationState.UIApplicationStateInactive -> false
            UIApplicationState.UIApplicationStateActive -> true
            else -> false
        }
    }
}