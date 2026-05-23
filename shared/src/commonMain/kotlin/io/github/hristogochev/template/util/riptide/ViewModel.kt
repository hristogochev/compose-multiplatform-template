package io.github.hristogochev.template.util.riptide

import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.NavEffect
import io.github.hristogochev.vortex.model.ScreenModel
import io.github.hristogochev.vortex.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ViewModel<State, Event, EffectState, NavEvent, NavEffectState>(
    initialState: State,
    initialEffect: EffectState? = null,
) : ScreenModel {

    protected val viewModelScope = screenModelScope

    protected val heavyViewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    protected val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _effect: MutableStateFlow<Effect<EffectState>?> = MutableStateFlow(
        if (initialEffect != null) {
            Effect(initialEffect, false, ::onConsumedEffect)
        } else {
            null
        }
    )

    val effect = _effect

    private val _isNavigating = MutableStateFlow<Boolean>(false)

    private var navJob: Job? = null

    protected val _navEffect: MutableStateFlow<NavEffect<NavEffectState>?> = MutableStateFlow(null)
    val navEffect = _navEffect


    open fun onEvent(event: Event) {

    }

    protected open suspend fun onNavEventProtected(event: NavEvent) {

    }

    fun onNavEvent(event: NavEvent) {
        val isNavigating = _isNavigating.getAndUpdate { true }
        if (isNavigating) return
        navJob = viewModelScope.launch {
            onNavEventProtected(event)
        }.apply {
            invokeOnCompletion {
                _isNavigating.update { false }
                onNavEventCompleted(event = event)
            }
        }
    }

    protected open fun onNavEventCompleted(event: NavEvent) {

    }

    fun cancelNavEvent() {
        navJob?.cancel()
    }

    private fun onConsumedEffect() {
        _effect.update { null }
    }

    protected fun <T> Flow<T>.stateInViewModel(
        initialValue: T,
        scope: CoroutineScope = heavyViewModelScope,
        started: SharingStarted = SharingStarted.Lazily,
    ): StateFlow<T> = stateIn(scope, started, initialValue)

    override fun onDispose() {
        heavyViewModelScope.cancel()
    }
}