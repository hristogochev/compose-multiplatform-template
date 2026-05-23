package io.github.hristogochev.template.data.repository

import com.russhwolf.settings.Settings
import io.github.hristogochev.template.domain.repository.FirstLoginNotificationPermissionRequestedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SettingsFirstRepository(private val settings: Settings) :
    FirstLoginNotificationPermissionRequestedRepository {

    companion object {
        private const val FIRST_SET = "FIRST_SET"
    }

    override suspend fun getAndSet(): Boolean {
        return withContext(Dispatchers.IO) {
            val isAlreadySet = runCatching {
                settings.getStringOrNull(FIRST_SET)
            }.getOrNull() != null
            if (isAlreadySet) return@withContext true
            else {
                runCatching {
                    settings.putString(FIRST_SET, "")
                }
                return@withContext false
            }
        }
    }
}