package io.github.hristogochev.template.data.repository.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import io.github.hristogochev.template.SQLDelightEntityDatabase
import io.github.hristogochev.template.UserDetailsLocal
import io.github.hristogochev.template.domain.repository.local.UserDetailsLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

class SQLUserDetailsLocalRepository(db: SQLDelightEntityDatabase) : UserDetailsLocalRepository {

    private val queries = db.userDetailsLocalQueries
    override suspend fun upsertAll(idsToRemove: List<ULong>, values: List<UserDetailsLocal>) {
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

    override suspend fun upsert(value: UserDetailsLocal) {
        withContext(Dispatchers.IO) {
            upsertBlockingSafe(value)
        }
    }

    override suspend fun updateFullNameById(firstName: String?, lastName: String?, id: ULong) {
        withContext(Dispatchers.IO) {
            queries.updateFirstAndLastNameById(
                firstName,
                lastName,
                id
            )
        }
    }

    override suspend fun updateNotificationsEnabledById(notificationsEnabled: Boolean, id: ULong) {
        withContext(Dispatchers.IO) {
            queries.updateNotificationsEnabledById(
                notificationsEnabled,
                id
            )
        }
    }


    override fun getByIdFlow(id: ULong): Flow<UserDetailsLocal?> {
        return queries.getById(id).asFlow().mapToOneOrNull(Dispatchers.IO)
            .distinctUntilChanged()
    }

    override fun upsertBlocking(value: UserDetailsLocal) {
        if (queries.exists(value.id).executeAsOne()) {
            queries.update(
                id = value.id,
                phoneNumber = value.phoneNumber,
                firstName = value.firstName,
                lastName = value.lastName,
                notificationsEnabled = value.notificationsEnabled
            )
        } else {
            queries.insert(
                id = value.id,
                phoneNumber = value.phoneNumber,
                firstName = value.firstName,
                lastName = value.lastName,
                notificationsEnabled = value.notificationsEnabled
            )
        }
    }
}