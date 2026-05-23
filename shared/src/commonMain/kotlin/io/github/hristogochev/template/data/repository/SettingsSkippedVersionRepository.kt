package io.github.hristogochev.template.data.repository

import com.russhwolf.settings.Settings
import io.github.hristogochev.template.domain.repository.SkippedVersionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class SettingsSkippedVersionRepository(private val settings: Settings) : SkippedVersionRepository {

    companion object {
        private const val SKIPPED_VERSION = "SKIPPED_VERSION"
    }

    override suspend fun get(): ULong? {
        return withContext(Dispatchers.IO) {
            runCatching {
                settings.getStringOrNull(SKIPPED_VERSION)?.toULongOrNull()
            }.getOrNull()
        }
    }

    override suspend fun save(versionCode: ULong) {
        return withContext(Dispatchers.IO) {
            runCatching {
                settings.putString(SKIPPED_VERSION, versionCode.toString())
            }
        }
    }

    override suspend fun clear() {
        return withContext(Dispatchers.IO) {
            runCatching {
                settings.remove(SKIPPED_VERSION)
            }
        }
    }
}