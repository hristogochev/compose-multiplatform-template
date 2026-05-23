package io.github.hristogochev.template

import android.app.Application
import io.github.hristogochev.template.di.androidModule
import io.github.hristogochev.template.di.communicatorsModule
import io.github.hristogochev.template.di.httpClientModule
import io.github.hristogochev.template.di.miscModule
import io.github.hristogochev.template.di.repositoryModule
import io.github.hristogochev.template.di.sqlDriverModule
import io.github.hristogochev.template.di.userModule
import io.github.hristogochev.template.di.viewModelModule
import io.github.hristogochev.template.util.notification.LocalNotificationManager
import io.github.hristogochev.template.util.notification.PushNotificationManager
import io.github.hristogochev.template.util.notification.AndroidLocalNotificationChannelFactory
import io.github.hristogochev.template.util.notification.AndroidLocalNotificationManager
import io.github.hristogochev.template.util.notification.AndroidPushNotificationManager
import io.github.hristogochev.template.util.notification.EventNotificationManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import kotlin.also
import kotlin.collections.plus

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidLocalNotificationChannelFactory(
            context = applicationContext,
            channelId = "DEFAULT_NOTIFICATION_CHANNEL_ID",
        ).also {
            it.createChannels()
        }

        LocalNotificationManager.shared = AndroidLocalNotificationManager(
            context = applicationContext,
            notificationIconResId = R.drawable.template_image,
            notificationIconColorResId = R.color.ic_launcher_background,
            channelId = "DEFAULT_NOTIFICATION_CHANNEL_ID",
        )

        EventNotificationManager.shared = EventNotificationManager()

        PushNotificationManager.shared = AndroidPushNotificationManager()

        startKoin {
            androidContext(this@AndroidApp)
            androidLogger()
            modules(androidModule() + miscModule() + userModule() + sqlDriverModule() + httpClientModule() + repositoryModule + communicatorsModule + viewModelModule())
        }
    }
}
