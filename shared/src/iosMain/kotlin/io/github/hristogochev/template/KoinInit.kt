package io.github.hristogochev.template


import io.github.hristogochev.template.di.communicatorsModule
import io.github.hristogochev.template.di.httpClientModule
import io.github.hristogochev.template.di.miscModule
import io.github.hristogochev.template.di.repositoryModule
import io.github.hristogochev.template.di.sqlDriverModule
import io.github.hristogochev.template.di.userModule
import io.github.hristogochev.template.di.viewModelModule
import io.github.hristogochev.template.util.notification.EventNotificationManager
import io.github.hristogochev.template.util.notification.IOSLocalNotificationManager
import io.github.hristogochev.template.util.notification.IOSPushNotificationManager
import io.github.hristogochev.template.util.notification.LocalNotificationManager
import io.github.hristogochev.template.util.notification.PushNotificationManager
import org.koin.core.context.startKoin

@Suppress("unused")
fun initKoin() {
    LocalNotificationManager.shared = IOSLocalNotificationManager()
    PushNotificationManager.shared = IOSPushNotificationManager()
    EventNotificationManager.shared = EventNotificationManager()


    // start Koin
    @Suppress("UNUSED_VARIABLE") val koinApp = startKoin {
        modules(miscModule() + userModule() + sqlDriverModule() + httpClientModule() + repositoryModule + communicatorsModule + viewModelModule())
    }.koin
}