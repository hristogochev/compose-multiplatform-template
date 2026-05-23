package io.github.hristogochev.template.data.repository.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.github.hristogochev.template.RegionLocal
import io.github.hristogochev.template.SQLDelightEntityDatabase
import io.github.hristogochev.template.domain.repository.local.RegionLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class SQLRegionLocalRepository(db: SQLDelightEntityDatabase) : RegionLocalRepository {

    private val queries = db.regionLocalQueries
    override suspend fun upsertAll(idsToRemove: List<UInt>, values: List<RegionLocal>) {
        withContext(Dispatchers.IO) {
            val removeIds = idsToRemove.toSet()
            val valueIds = values.map { it.id }.toSet()
            val diffIds = removeIds - valueIds
            queries.transaction {
                if (diffIds.isNotEmpty()) {
                    queries.deleteAllByIds(diffIds)
                }
                values.forEach { value ->
                    upsertBlockingSafe(value)
                }
            }
        }
    }

    override suspend fun upsert(value: RegionLocal) {
        withContext(Dispatchers.IO) {
            upsertBlockingSafe(value)
        }
    }

    override suspend fun getAll(): List<RegionLocal> {
        return withContext(Dispatchers.IO) {
            queries.getAll().executeAsList()
        }
    }

    override fun getAllFlow(): Flow<List<RegionLocal>> {
        return queries.getAll()
            .asFlow()
            .mapToList(Dispatchers.IO).distinctUntilChanged()
    }


    override fun getAllInIdsFlow(ids: List<UInt>): Flow<List<RegionLocal>> {
        return queries.getAllInIds(ids)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .distinctUntilChanged()
    }

    override fun getByIdFlow(id: UInt): Flow<RegionLocal?> {
        return queries.getById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .distinctUntilChanged()
    }


    override suspend fun getAllInCityIds(cityIds: List<ULong>): List<RegionLocal> {
        return withContext(Dispatchers.IO) {
            queries.getAllInCityIds(cityIds).executeAsList()
        }
    }


    override fun upsertBlocking(value: RegionLocal) {
        if (queries.exists(value.id).executeAsOne()) {
            queries.update(
                id = value.id,
                name = value.name,
                countryId = value.countryId
            )
        } else {
            queries.insert(
                id = value.id,
                name = value.name,
                countryId = value.countryId
            )
        }
    }
}