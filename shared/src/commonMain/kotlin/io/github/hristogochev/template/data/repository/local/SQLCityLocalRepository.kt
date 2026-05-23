package io.github.hristogochev.template.data.repository.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.github.hristogochev.template.CityLocal
import io.github.hristogochev.template.SQLDelightEntityDatabase
import io.github.hristogochev.template.domain.repository.local.CityLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class SQLCityLocalRepository(db: SQLDelightEntityDatabase) : CityLocalRepository {

    private val queries = db.cityLocalQueries
    override suspend fun upsertAll(idsToRemove: List<ULong>, values: List<CityLocal>) {
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

    override suspend fun upsert(value: CityLocal) {
        withContext(Dispatchers.IO) {
            upsertBlockingSafe(value)
        }
    }

    override suspend fun getAll(): List<CityLocal> {
        return withContext(Dispatchers.IO) {
            queries.getAll().executeAsList()
        }
    }


    override fun getAllFlow(): Flow<List<CityLocal>> {
        return queries.getAll()
            .asFlow()
            .mapToList(Dispatchers.IO).distinctUntilChanged()
    }


    override fun getAllInIdsFlow(ids: List<ULong>): Flow<List<CityLocal>> {
        return queries.getAllInIds(ids)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .distinctUntilChanged()
    }

    override fun getByIdFlow(id: ULong): Flow<CityLocal?> {
        return queries.getById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .distinctUntilChanged()
    }


    override fun upsertBlocking(value: CityLocal) {
        if (queries.exists(value.id).executeAsOne()) {
            queries.update(
                id = value.id,
                name = value.name,
                regionId = value.regionId,
            )
        } else {
            queries.insert(
                id = value.id,
                name = value.name,
                regionId = value.regionId,
            )
        }
    }
}