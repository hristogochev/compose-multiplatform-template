package io.github.hristogochev.template.presentation.auth.register_second

import androidx.compose.runtime.Composable
import io.github.hristogochev.vortex.koin.koinScreenModel
import io.github.hristogochev.vortex.navigator.Navigator
import org.koin.core.parameter.parametersOf
import io.github.hristogochev.template.presentation.auth.register_success.RegisterSuccessScreenNav
import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.riptide.ScreenNav
import io.github.hristogochev.template.util.riptide.ViewModel

@Parcelize
data class RegisterSecondScreenNav(
    private val initialState: RegisterSecondState,
) :
    ScreenNav<RegisterSecondState, RegisterSecondEvent, RegisterSecondEffect, RegisterSecondNavEvent, RegisterSecondNavEffect>(
        initialState
    ), Parcelable {

    override suspend fun onNavigate(navigator: Navigator, it: RegisterSecondNavEffect) {
        when (it) {
            is RegisterSecondNavEffect.Continue -> {
                navigator.replaceAll(
                    RegisterSuccessScreenNav(
                        it.initialState
                    )
                )
            }

            RegisterSecondNavEffect.Back -> {
                navigator.pop()
            }
        }
    }

    @Composable
    override fun viewModel(initialState: RegisterSecondState): ViewModel<RegisterSecondState, RegisterSecondEvent, RegisterSecondEffect, RegisterSecondNavEvent, RegisterSecondNavEffect> {
        return koinScreenModel<RegisterSecondViewModel>(parameters = { parametersOf(initialState) })
    }

    @Composable
    override fun Content(
        state: RegisterSecondState,
        effect: Effect<RegisterSecondEffect>?,
        onEvent: (RegisterSecondEvent) -> Unit,
        onNavEvent: (RegisterSecondNavEvent) -> Unit,
    ) {
        RegisterSecondScreen(
            state = state,
            onEvent = onEvent,
            effect = effect,
            onNavEvent = onNavEvent
        )
    }
}