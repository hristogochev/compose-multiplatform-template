package io.github.hristogochev.template.domain.repository.local

import io.github.hristogochev.template.UserDetailsLocal
import io.github.hristogochev.template.domain.repository.LocalRepositoryTemplate
import kotlinx.coroutines.flow.Flow

interface UserDetailsLocalRepository : LocalRepositoryTemplate<UserDetailsLocal> {
    suspend fun upsertAll(idsToRemove: List<ULong>, values: List<UserDetailsLocal>)
    suspend fun upsert(value: UserDetailsLocal)
    suspend fun updateFullNameById(firstName: String?, lastName: String?, id: ULong)
    suspend fun updateNotificationsEnabledById(notificationsEnabled: Boolean, id: ULong)
    fun getByIdFlow(id: ULong): Flow<UserDetailsLocal?>
}