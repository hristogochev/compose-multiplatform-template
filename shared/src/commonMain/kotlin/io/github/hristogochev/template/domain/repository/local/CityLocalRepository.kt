package io.github.hristogochev.template.domain.repository.local

import io.github.hristogochev.template.domain.repository.LocalRepositoryTemplate
import kotlinx.coroutines.flow.Flow
import io.github.hristogochev.template.CityLocal

interface CityLocalRepository : LocalRepositoryTemplate<CityLocal> {
    suspend fun upsertAll(idsToRemove: List<ULong>, values: List<CityLocal>)
    suspend fun upsert(value: CityLocal)
    suspend fun getAll(): List<CityLocal>
    fun getAllFlow(): Flow<List<CityLocal>>
    fun getAllInIdsFlow(ids: List<ULong>): Flow<List<CityLocal>>
    fun getByIdFlow(id: ULong): Flow<CityLocal?>
}