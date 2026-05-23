package io.github.hristogochev.template.util.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import io.github.hristogochev.template.util.notification.LocalNotificationManager
import io.github.hristogochev.template.util.toUriOrNull
import kotlin.random.Random


class AndroidLocalNotificationManager(
    private val context: Context,
    private val notificationIconResId: Int,
    private val channelId: String,
    private val notificationIconColorResId: Int? = null,
) : LocalNotificationManager {

    companion object {
        const val KEY_URL: String = "URL"
    }

    override fun notify(
        title: String,
        body: String,
        payloadData: Map<String, String>,
    ): Int {
        val notificationId = Random.nextInt(0, Int.MAX_VALUE)

        val notificationManager = context.notificationManager ?: return -1

        val pendingIntent = createPendingIntent(payloadData)

        val notification = NotificationCompat.Builder(
            context,
            channelId
        ).apply {
            setContentTitle(title)
            setContentText(body)
            setSmallIcon(notificationIconResId)
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            if (notificationIconColorResId != null) {
                color = ContextCompat.getColor(context, notificationIconColorResId)
            }
        }.build()

        notificationManager.notify(notificationId, notification)

        return notificationId
    }

    override fun remove(id: Int) {
        val notificationManager = context.notificationManager ?: return
        notificationManager.cancel(id)
    }

    override fun removeAll() {
        val notificationManager = context.notificationManager ?: return
        notificationManager.cancelAll()
    }

    private fun createPendingIntent(payloadData: Map<String, String>): PendingIntent? {

        val intent = getLauncherActivityIntent()?.apply {
            putExtra(ANDROID_NOTIFICATION_INTENT_HINT, ANDROID_NOTIFICATION_INTENT_HINT)
            payloadData.forEach { putExtra(it.key, it.value) }
            val uri = payloadData.getOrDefault(KEY_URL, null)?.toUriOrNull()
            if (uri != null) {
                this.data = uri
            }
        } ?: return null

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        return PendingIntent.getActivity(context, 0, intent, flags)
    }

    private fun getLauncherActivityIntent(): Intent? {
        val packageManager = context.applicationContext.packageManager
        return packageManager.getLaunchIntentForPackage(context.applicationContext.packageName)
    }
}

internal val Context.notificationManager
    get() = this.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager