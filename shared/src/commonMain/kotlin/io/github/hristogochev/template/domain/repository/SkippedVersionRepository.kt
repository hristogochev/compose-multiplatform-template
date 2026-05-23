package io.github.hristogochev.template.domain.repository

interface SkippedVersionRepository {
    suspend fun get(): ULong?
    suspend fun save(versionCode: ULong)
    suspend fun clear()
}