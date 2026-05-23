package io.github.hristogochev.template.domain.repository.local

import io.github.hristogochev.template.domain.repository.LocalRepositoryTemplate
import kotlinx.coroutines.flow.Flow
import io.github.hristogochev.template.RegionLocal

interface RegionLocalRepository : LocalRepositoryTemplate<RegionLocal> {
    suspend fun upsertAll(idsToRemove: List<UInt>, values: List<RegionLocal>)
    suspend fun upsert(value: RegionLocal)
    suspend fun getAll(): List<RegionLocal>
    fun getAllFlow(): Flow<List<RegionLocal>>
    suspend fun getAllInCityIds(cityIds: List<ULong>): List<RegionLocal>
    fun getAllInIdsFlow(ids: List<UInt>): Flow<List<RegionLocal>>
    fun getByIdFlow(id: UInt): Flow<RegionLocal?>
}