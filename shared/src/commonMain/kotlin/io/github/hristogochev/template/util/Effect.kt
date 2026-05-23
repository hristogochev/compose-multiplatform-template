package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class Effect<T>(
    val data: T,
    val instant: Boolean,
    val onConsumed: () -> Unit
)


fun <T> MutableStateFlow<Effect<T>?>.updateEffect(effect: T, instant: Boolean = true) {
    val flow = this
    val onConsumed = {
        flow.update { null }
    }
    flow.update {
        Effect(effect, instant, onConsumed)
    }
}

@Composable
fun <T> consumeEffect(effect: Effect<T>?, consume: suspend (T) -> Unit) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(effect) {
        if (effect == null) return@LaunchedEffect
        if (effect.instant) {
            scope.launch {
                consume(effect.data)
            }
        } else {
            consume(effect.data)
        }
        effect.onConsumed()
    }
}

fun <T> StateFlow<Effect<T>?>.onEachEffect(
    scope: CoroutineScope,
    consume: suspend (T) -> Unit
) {
    onEach {
        if (it == null) return@onEach
        consume(it.data)
        it.onConsumed()
    }.launchIn(scope)
}

suspend fun <T> MutableStateFlow<Effect<T>?>.consumeFirstNotNull(): T {
    val flow = this
    val first = flow.first { it != null } as Effect<T>
    first.onConsumed()
    flow.first { it == null }
    return first.data
}
