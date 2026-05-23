package io.github.hristogochev.template.presentation.update

import androidx.compose.runtime.Composable
import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.riptide.ScreenNav
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.vortex.koin.koinScreenModel
import io.github.hristogochev.vortex.navigator.Navigator
import org.koin.core.parameter.parametersOf

@Parcelize
data class NeedsUpdateScreenNav(private val initialState: NeedsUpdateState) :
    ScreenNav<NeedsUpdateState, Nothing, Nothing, NeedsUpdateNavEvent, NeedsUpdateNavEffect>(
        initialState
    ),
    Parcelable {
    override suspend fun onNavigate(navigator: Navigator, it: NeedsUpdateNavEffect) {
        when (it) {
            NeedsUpdateNavEffect.Skip -> {
                navigator.pop()
            }
        }
    }

    @Composable
    override fun viewModel(initialState: NeedsUpdateState): ViewModel<NeedsUpdateState, Nothing, Nothing, NeedsUpdateNavEvent, NeedsUpdateNavEffect> {
        return koinScreenModel<NeedsUpdateViewModel> { parametersOf(initialState) }
    }

    @Composable
    override fun Content(
        state: NeedsUpdateState,
        effect: Effect<Nothing>?,
        onEvent: (Nothing) -> Unit,
        onNavEvent: (NeedsUpdateNavEvent) -> Unit,
    ) {
        NeedsUpdateScreen(state = state, onNavEvent = onNavEvent)
    }
}