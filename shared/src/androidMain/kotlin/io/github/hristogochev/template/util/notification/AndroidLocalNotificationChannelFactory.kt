package io.github.hristogochev.template.util.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import io.github.hristogochev.template.util.toUriOrNull

class AndroidLocalNotificationChannelFactory(
    private val context: Context,
    private val channelId: String,
    private val channelName: String = "General",
    private val channelDescription: String = "",
    private val channelSoundUri: String? = null,
) {

    fun createChannels() {
        val notificationManager = context.notificationManager ?: return

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            this.description = channelDescription
            enableLights(true)
            channelSoundUri?.also { setSound(it) }
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun NotificationChannel.setSound(soundUri: String): Result<Unit> {
        return runCatching {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build() ?: return@runCatching


            val uri = soundUri.toUriOrNull() ?: return@runCatching

            setSound(uri, audioAttributes)
        }
    }
}