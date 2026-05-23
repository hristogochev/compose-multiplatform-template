package io.github.hristogochev.template.util.riptide

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.consumeNavEffect
import io.github.hristogochev.vortex.navigator.LocalNavigator
import io.github.hristogochev.vortex.navigator.Navigator
import io.github.hristogochev.vortex.screen.Screen
import io.github.hristogochev.vortex.util.currentOrThrow


abstract class ScreenNav<State, Event, EffectState, NavEvent, NavEffectState>(
    private val initialState: State,
) : Screen {


    abstract suspend fun onNavigate(navigator: Navigator, it: NavEffectState)

    @Composable
    abstract fun viewModel(initialState: State): ViewModel<State, Event, EffectState, NavEvent, NavEffectState>

    @Composable
    abstract fun Content(
        state: State,
        effect: Effect<EffectState>?,
        onEvent: (Event) -> Unit,
        onNavEvent: (NavEvent) -> Unit,
    )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = viewModel(initialState)

        val state by viewModel.state.collectAsState()
        val effect by viewModel.effect.collectAsState()
        val navEffect by viewModel.navEffect.collectAsState()
        val onEvent = viewModel::onEvent
        val onNavEvent = viewModel::onNavEvent

        consumeNavEffect(navEffect) {
            onNavigate(navigator, it)
        }

        Content(state, effect, onEvent, onNavEvent)
    }
}
