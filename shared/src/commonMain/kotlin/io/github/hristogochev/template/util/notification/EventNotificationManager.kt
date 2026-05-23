package io.github.hristogochev.template.util.notification

import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.updateEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EventNotificationManager {

    val scope: CoroutineScope =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())


    val onNotificationClicked: MutableStateFlow<Effect<Map<String, String>>?> =
        MutableStateFlow(null)

    val onNotificationReceivedInForeground: MutableStateFlow<Effect<ForegroundNotification>?> =
        MutableStateFlow(null)

    companion object {
        lateinit var shared: EventNotificationManager
    }


    fun notificationReceivedInForeground(notification: ForegroundNotification) {
        scope.launch {
            onNotificationReceivedInForeground.updateEffect(notification)
        }
    }

    fun notificationClicked(data: Map<String, String>) {
        scope.launch {
            onNotificationClicked.updateEffect(data)
        }
    }
}