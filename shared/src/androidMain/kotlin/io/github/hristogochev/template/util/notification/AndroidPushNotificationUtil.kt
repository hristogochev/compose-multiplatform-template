package io.github.hristogochev.template.util.notification

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.core.os.bundleOf
import androidx.core.util.Consumer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.github.hristogochev.template.util.mapNotNullValues
import io.github.hristogochev.template.util.toMap

fun ComponentActivity.notificationClickHandler() {
    val listener = Consumer<Intent> {
        consumeNotificationClick(it)
    }

    lifecycle.addObserver(
        object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                this@notificationClickHandler.addOnNewIntentListener(listener)
                super.onCreate(owner)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                this@notificationClickHandler.removeOnNewIntentListener(listener)
                super.onDestroy(owner)
            }
        }
    )
}

fun ComponentActivity.getNotificationClick(): Map<String, String>? {
    val extras = intent.extras ?: return null

    val intentCameFromNotification =
        extras.containsKey(ANDROID_NOTIFICATION_INTENT_HINT)

    if (!intentCameFromNotification) return null

    return extras.toMap().mapNotNullValues {
        it?.toString()
    }
}


fun consumeNotificationClick(intent: Intent) {
    val extras = intent.extras ?: bundleOf()

    val isNotificationClicked =
        extras.containsKey(ANDROID_NOTIFICATION_INTENT_HINT)

    if (isNotificationClicked) {

        val payloadData = extras.toMap().mapNotNullValues {
            it?.toString()
        }

        EventNotificationManager.shared.notificationClicked(payloadData)
    }
}