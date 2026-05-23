package io.github.hristogochev.template.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

inline fun <reified T> List<Flow<List<T>>>.combine(): Flow<List<T>> {
    return if (this.isEmpty()) {
        flowOf(emptyList()) // Return empty list if no flows exist
    } else {
        combine(this) { it.toList().flatten() }
    }
}
