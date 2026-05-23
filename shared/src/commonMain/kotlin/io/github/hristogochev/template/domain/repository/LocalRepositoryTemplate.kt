package io.github.hristogochev.template.domain.repository

interface LocalRepositoryTemplate<T> {
    fun upsertBlocking(value: T)
    fun upsertBlockingSafe(value: T) {
        runCatching {
            upsertBlocking(value)
        }.onFailure {
            println("Upsert failed for: $value")
        }
    }
}