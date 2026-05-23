package io.github.hristogochev.template.presentation.auth.login_first

import androidx.compose.runtime.Composable
import io.github.hristogochev.template.presentation.auth.login_second.LoginSecondScreenNav
import io.github.hristogochev.template.presentation.auth.register_first.RegisterFirstScreenNav
import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.template.util.riptide.ScreenNav
import io.github.hristogochev.template.util.riptide.ViewModel
import io.github.hristogochev.vortex.koin.koinScreenModel
import io.github.hristogochev.vortex.navigator.Navigator
import org.koin.core.parameter.parametersOf

@Parcelize
data class LoginFirstScreenNav(private val initialState: LoginFirstState) :
    ScreenNav<LoginFirstState, LoginFirstEvent, LoginFirstEffect, LoginFirstNavEvent, LoginFirstNavEffect>(
        initialState
    ),
    Parcelable {
    override suspend fun onNavigate(navigator: Navigator, it: LoginFirstNavEffect) {
        when (it) {
            is LoginFirstNavEffect.Continue -> {
                navigator.push(LoginSecondScreenNav(it.initialState))
            }

            is LoginFirstNavEffect.Register -> {
                navigator.replace(RegisterFirstScreenNav(it.initialState))
            }
        }
    }

    @Composable
    override fun viewModel(initialState: LoginFirstState): ViewModel<LoginFirstState, LoginFirstEvent, LoginFirstEffect, LoginFirstNavEvent, LoginFirstNavEffect> {
        return koinScreenModel<LoginFirstViewModel> { parametersOf(initialState) }
    }

    @Composable
    override fun Content(
        state: LoginFirstState,
        effect: Effect<LoginFirstEffect>?,
        onEvent: (LoginFirstEvent) -> Unit,
        onNavEvent: (LoginFirstNavEvent) -> Unit,
    ) {
        LoginFirstScreen(
            state = state,
            onEvent = onEvent,
            effect = effect,
            onNavEvent = onNavEvent
        )
    }
}