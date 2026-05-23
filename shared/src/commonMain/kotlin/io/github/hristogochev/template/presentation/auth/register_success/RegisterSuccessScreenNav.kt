package io.github.hristogochev.template.presentation.auth.register_success

import androidx.compose.runtime.Composable
import io.github.hristogochev.template.presentation.home.HomeScreenNav
import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.riptide.ScreenNav
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.vortex.koin.koinScreenModel
import io.github.hristogochev.vortex.navigator.Navigator
import org.koin.core.parameter.parametersOf

@Parcelize
data class RegisterSuccessScreenNav(
    private val initialState: RegisterSuccessState,
) : ScreenNav<RegisterSuccessState, Nothing, Nothing, RegisterSuccessNavEvent, RegisterSuccessNavEffect>(
    initialState
), Parcelable {

    override suspend fun onNavigate(navigator: Navigator, it: RegisterSuccessNavEffect) {
        when (it) {
            is RegisterSuccessNavEffect.Continue -> {
                navigator.replaceAll(
                    HomeScreenNav(
                        it.homeInitialState
                    )
                )
            }
        }
    }

    @Composable
    override fun viewModel(initialState: RegisterSuccessState): ViewModel<RegisterSuccessState, Nothing, Nothing, RegisterSuccessNavEvent, RegisterSuccessNavEffect> {
        return koinScreenModel<RegisterSuccessViewModel> { parametersOf(initialState) }
    }

    @Composable
    override fun Content(
        state: RegisterSuccessState,
        effect: Effect<Nothing>?,
        onEvent: (Nothing) -> Unit,
        onNavEvent: (RegisterSuccessNavEvent) -> Unit,
    ) {
        RegisterSuccessScreen(state = state, onNavEvent = onNavEvent)
    }
}