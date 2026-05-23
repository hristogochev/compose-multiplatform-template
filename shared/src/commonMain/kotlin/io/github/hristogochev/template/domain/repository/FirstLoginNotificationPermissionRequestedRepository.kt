package io.github.hristogochev.template.domain.repository

interface FirstLoginNotificationPermissionRequestedRepository {
    suspend fun getAndSet(): Boolean
}