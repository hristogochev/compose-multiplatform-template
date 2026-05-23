package io.github.hristogochev.template.domain.repository

import io.github.hristogochev.template.util.timeNow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Instant

class CurrentDateTimeRepository {
    private val scope = CoroutineScope(Dispatchers.Default)
    val dateTime = flow {
        var currentTime: Instant
        while (true) {
            delay(5000)
            currentTime = timeNow()
            emit(currentTime)
        }
    }.stateIn(scope, SharingStarted.Lazily, timeNow())
}