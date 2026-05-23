package io.github.hristogochev.template.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

open class Communicator<TxData, RxData> {
    private val _tx = MutableStateFlow<Effect<TxData>?>(null)
    private val _rx = MutableStateFlow<Effect<RxData>?>(null)

    fun consume(scope: CoroutineScope, consume: suspend (TxData) -> RxData) {
        _tx.onEachEffect(scope) {
            val data = consume(it)
            _rx.updateEffect(data)
        }
    }

    suspend fun sendMessage(data: TxData): RxData {
        _tx.updateEffect(data)
        return _rx.consumeFirstNotNull()
    }

    fun clearMessage() {
        _tx.update { null }
    }
}