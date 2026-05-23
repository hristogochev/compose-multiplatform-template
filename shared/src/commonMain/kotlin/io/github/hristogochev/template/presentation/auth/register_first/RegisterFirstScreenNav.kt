package io.github.hristogochev.template.presentation.auth.register_first

import androidx.compose.runtime.Composable
import io.github.hristogochev.vortex.koin.koinScreenModel
import io.github.hristogochev.vortex.navigator.Navigator
import org.koin.core.parameter.parametersOf
import io.github.hristogochev.template.presentation.auth.login_first.LoginFirstScreenNav
import io.github.hristogochev.template.presentation.auth.register_second.RegisterSecondScreenNav
import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.riptide.ScreenNav
import io.github.hristogochev.template.util.riptide.ViewModel


@Parcelize
data class RegisterFirstScreenNav(private val initialState: RegisterFirstState) :
    ScreenNav<RegisterFirstState, RegisterFirstEvent, RegisterFirstEffect, RegisterFirstNavEvent, RegisterFirstNavEffect>(
        initialState
    ),
    Parcelable {
    override suspend fun onNavigate(navigator: Navigator, it: RegisterFirstNavEffect) {
        when (it) {
            is RegisterFirstNavEffect.Continue -> {
                navigator.push(RegisterSecondScreenNav(it.initialState))
            }

            is RegisterFirstNavEffect.Login -> {
                navigator.replace(LoginFirstScreenNav(it.initialState))
            }
        }
    }

    @Composable
    override fun viewModel(initialState: RegisterFirstState): ViewModel<RegisterFirstState, RegisterFirstEvent, RegisterFirstEffect, RegisterFirstNavEvent, RegisterFirstNavEffect> {
        return koinScreenModel<RegisterFirstViewModel> {
            parametersOf(initialState)
        }
    }

    @Composable
    override fun Content(
        state: RegisterFirstState,
        effect: Effect<RegisterFirstEffect>?,
        onEvent: (RegisterFirstEvent) -> Unit,
        onNavEvent: (RegisterFirstNavEvent) -> Unit,
    ) {
        RegisterFirstScreen(
            state = state,
            onEvent = onEvent,
            effect = effect,
            onNavEvent = onNavEvent
        )
    }
}