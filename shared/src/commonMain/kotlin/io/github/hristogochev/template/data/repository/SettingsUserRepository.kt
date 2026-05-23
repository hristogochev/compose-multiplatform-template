@file:OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)

package io.github.hristogochev.template.data.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.serialization.removeValue
import io.github.hristogochev.template.data.entity.response.UserCredentialsEntity
import io.github.hristogochev.template.data.mapper.response.toUserCredentials
import io.github.hristogochev.template.data.mapper.response.toUserLocalEntity
import io.github.hristogochev.template.domain.entity.auth.UserCredentials
import io.github.hristogochev.template.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

class SettingsUserRepository(private val settings: Settings) : UserRepository {

    private val userSerializer = UserCredentialsEntity.serializer()

    companion object {
        private val LOCAL_ID = 1u.toString()
    }

    private val _userCredentials = MutableStateFlow(getBlocking())
    override val credentials: StateFlow<UserCredentials?> = _userCredentials.asStateFlow()

    override fun getBlocking(): UserCredentials? {
        return runCatching {
            settings.decodeValueOrNull(userSerializer, LOCAL_ID)?.toUserCredentials()
        }.getOrNull()
    }

    override suspend fun get(): UserCredentials? {
        return withContext(Dispatchers.IO) {
            runCatching {
                settings.decodeValueOrNull(userSerializer, LOCAL_ID)?.toUserCredentials()
            }.getOrNull()
        }
    }

    override suspend fun save(userCredentials: UserCredentials) {
        return withContext(Dispatchers.IO) {
            runCatching {
                settings.encodeValue(
                    userSerializer,
                    LOCAL_ID,
                    userCredentials.toUserLocalEntity()
                )
            }.also {
                val storedCredentials = get()
                _userCredentials.update { storedCredentials }
            }
        }
    }

    override suspend fun clear() {
        return withContext(Dispatchers.IO) {
            runCatching {
                settings.removeValue(userSerializer, LOCAL_ID)
            }.also {
                val storedCredentials = get()
                _userCredentials.update { storedCredentials }
            }
        }
    }
}