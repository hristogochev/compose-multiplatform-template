package io.github.hristogochev.template.util.notification

interface LocalNotificationManager {
    companion object {
        lateinit var shared: LocalNotificationManager
    }

    fun notify(title: String, body: String, payloadData: Map<String, String>): Int

    fun remove(id: Int)

    fun removeAll()
}