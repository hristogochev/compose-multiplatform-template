package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


data class NavEffect<T>(
    val data: T,
    val onConsumed: () -> Unit
)


fun <T> MutableStateFlow<NavEffect<T>?>.updateNavEffect(navEffect: T) {
    if (this.value != null) return
    val flow = this
    val onConsumed = {
        flow.update { null }
    }
    flow.update {
        NavEffect(navEffect, onConsumed)
    }
}

@Composable
fun <T> consumeNavEffect(effect: NavEffect<T>?, consume: suspend (T) -> Unit) {
    LaunchedEffect(effect) {
        if (effect == null) return@LaunchedEffect
        consume(effect.data)
        effect.onConsumed()
    }
}
