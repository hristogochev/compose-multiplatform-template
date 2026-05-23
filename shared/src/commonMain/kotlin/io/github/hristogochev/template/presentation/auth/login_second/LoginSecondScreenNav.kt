package io.github.hristogochev.template.presentation.auth.login_second

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
data class LoginSecondScreenNav(
    private val initialState: LoginSecondState,
) :
    ScreenNav<LoginSecondState, LoginSecondEvent, LoginSecondEffect, LoginSecondNavEvent, LoginSecondNavEffect>(
        initialState
    ), Parcelable {

    override suspend fun onNavigate(navigator: Navigator, it: LoginSecondNavEffect) {
        when (it) {
            is LoginSecondNavEffect.Continue -> {
                navigator.replaceAll(
                    HomeScreenNav(it.homeInitialState)
                )
            }

            LoginSecondNavEffect.Back -> {
                navigator.pop()
            }
        }
    }

    @Composable
    override fun viewModel(initialState: LoginSecondState): ViewModel<LoginSecondState, LoginSecondEvent, LoginSecondEffect, LoginSecondNavEvent, LoginSecondNavEffect> {
        return koinScreenModel<LoginSecondViewModel>(parameters = { parametersOf(initialState) })
    }

    @Composable
    override fun Content(
        state: LoginSecondState,
        effect: Effect<LoginSecondEffect>?,
        onEvent: (LoginSecondEvent) -> Unit,
        onNavEvent: (LoginSecondNavEvent) -> Unit,
    ) {
        LoginSecondScreen(
            state = state,
            onEvent = onEvent,
            effect = effect,
            onNavEvent = onNavEvent
        )
    }
}