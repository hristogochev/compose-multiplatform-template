package io.github.hristogochev.template.domain.repository.local

import io.github.hristogochev.template.CountryLocal
import io.github.hristogochev.template.domain.repository.LocalRepositoryTemplate
import kotlinx.coroutines.flow.Flow

interface CountryLocalRepository : LocalRepositoryTemplate<CountryLocal> {
    suspend fun upsertAll(idsToRemove: List<UInt>, values: List<CountryLocal>)
    suspend fun upsert(value: CountryLocal)
    suspend fun getAll(): List<CountryLocal>
    fun getAllFlow(): Flow<List<CountryLocal>>
    suspend fun getAllInRegionIds(regionIds: List<UInt>): List<CountryLocal>
    fun getAllInIdsFlow(ids: List<UInt>): Flow<List<CountryLocal>>
    fun getByIdFlow(id: UInt): Flow<CountryLocal?>
}