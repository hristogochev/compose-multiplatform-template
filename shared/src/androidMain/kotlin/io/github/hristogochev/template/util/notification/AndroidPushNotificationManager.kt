package io.github.hristogochev.template.util.notification

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.asDeferred

class AndroidPushNotificationManager : PushNotificationManager {


    private val _deviceToken = MutableStateFlow<String?>(null)

    override val deviceToken = _deviceToken.asStateFlow()

    override fun updateDeviceToken(deviceToken: String?) {
        _deviceToken.update { deviceToken }
    }

    override suspend fun getToken(): String? {
        return runCatching {
            FirebaseMessaging.getInstance().token.asDeferred().await()
        }.getOrNull()
    }

    override suspend fun deleteMyToken(): Boolean {
        runCatching {
            FirebaseMessaging.getInstance().deleteToken().asDeferred().await()
        }
        return true
    }

    override suspend fun subscribeToTopic(topic: String) {
        runCatching {
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }
    }

    override suspend fun unsubscribeFromTopic(topic: String) {
        runCatching {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
        }
    }

    /* IOS specific */
    private val _apnsTokenSet = MutableStateFlow(true)

    override val apnsTokenSet: StateFlow<Boolean> = _apnsTokenSet.asStateFlow()

    override fun setApnsToken() {
        return
    }

    override fun onApplicationDidReceiveRemoteNotification(userInfo: Map<Any?, *>): Boolean {
        return false
    }
}