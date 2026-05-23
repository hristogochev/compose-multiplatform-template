package io.github.hristogochev.template.util.notification

import kotlinx.coroutines.flow.StateFlow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "PushNotificationManager")
interface PushNotificationManager {

    companion object {
        lateinit var shared: PushNotificationManager
    }


    /* Shared */
    val deviceToken: StateFlow<String?>

    fun updateDeviceToken(deviceToken: String?)

    @ObjCName("getToken")
    suspend fun getToken(): String?

    @ObjCName("deleteMyToken")
    suspend fun deleteMyToken(): Boolean

    @ObjCName("subscribeToTopic")
    suspend fun subscribeToTopic(topic: String)

    @ObjCName("unsubscribeFromTopic")
    suspend fun unsubscribeFromTopic(topic: String)


    /* IOS specific */
    val apnsTokenSet: StateFlow<Boolean>

    @ObjCName("setApnsToken")
    fun setApnsToken()

    @ObjCName("onApplicationDidReceiveRemoteNotification")
    fun onApplicationDidReceiveRemoteNotification(userInfo: Map<Any?, *>): Boolean
}