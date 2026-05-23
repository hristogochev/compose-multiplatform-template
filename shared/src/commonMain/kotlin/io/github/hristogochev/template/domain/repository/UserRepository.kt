package io.github.hristogochev.template.domain.repository

import io.github.hristogochev.template.domain.entity.auth.UserCredentials
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val credentials: StateFlow<UserCredentials?>
    fun getBlocking(): UserCredentials?
    suspend fun get(): UserCredentials?
    suspend fun save(userCredentials: UserCredentials)
    suspend fun clear()
}