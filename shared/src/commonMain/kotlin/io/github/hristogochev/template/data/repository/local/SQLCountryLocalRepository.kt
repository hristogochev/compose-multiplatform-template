package io.github.hristogochev.template.data.repository.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.github.hristogochev.template.CountryLocal
import io.github.hristogochev.template.SQLDelightEntityDatabase
import io.github.hristogochev.template.domain.repository.local.CountryLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class SQLCountryLocalRepository(db: SQLDelightEntityDatabase) : CountryLocalRepository {

    private val queries = db.countryLocalQueries
    override suspend fun upsertAll(idsToRemove: List<UInt>, values: List<CountryLocal>) {
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

    override suspend fun upsert(value: CountryLocal) {
        withContext(Dispatchers.IO) {
            upsertBlockingSafe(value)
        }
    }

    override suspend fun getAll(): List<CountryLocal> {
        return withContext(Dispatchers.IO) {
            queries.getAll().executeAsList()
        }
    }

    override fun getAllFlow(): Flow<List<CountryLocal>> {
        return queries.getAll()
            .asFlow()
            .mapToList(Dispatchers.IO).distinctUntilChanged()
    }


    override fun getAllInIdsFlow(ids: List<UInt>): Flow<List<CountryLocal>> {
        return queries.getAllInIds(ids)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .distinctUntilChanged()
    }

    override fun getByIdFlow(id: UInt): Flow<CountryLocal?> {
        return queries.getById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .distinctUntilChanged()
    }


    override suspend fun getAllInRegionIds(regionIds: List<UInt>): List<CountryLocal> {
        return withContext(Dispatchers.IO) {
            queries.getAllInRegionIds(regionIds).executeAsList()
        }
    }


    override fun upsertBlocking(value: CountryLocal) {
        if (queries.exists(value.id).executeAsOne()) {
            queries.update(
                id = value.id,
                name = value.name
            )
        } else {
            queries.insert(
                id = value.id,
                name = value.name
            )
        }
    }
}