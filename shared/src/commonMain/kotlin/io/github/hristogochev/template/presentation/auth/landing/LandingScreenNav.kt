package io.github.hristogochev.template.presentation.auth.landing

import androidx.compose.runtime.Composable
import io.github.hristogochev.template.presentation.auth.login_first.LoginFirstScreenNav
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
data object LandingScreenNav :
    ScreenNav<LandingState, Nothing, Nothing, LandingNavEvent, LandingNavEffect>(LandingState),
    Parcelable {
    override suspend fun onNavigate(navigator: Navigator, it: LandingNavEffect) {
        when (it) {
            is LandingNavEffect.Login -> navigator.push(LoginFirstScreenNav(it.initialState))
            is LandingNavEffect.Register -> navigator.push(RegisterFirstScreenNav(it.initialState))
        }
    }

    @Composable
    override fun viewModel(initialState: LandingState): ViewModel<LandingState, Nothing, Nothing, LandingNavEvent, LandingNavEffect> {
        return koinScreenModel<LandingViewModel> { parametersOf(initialState) }
    }

    @Composable
    override fun Content(
        state: LandingState,
        effect: Effect<Nothing>?,
        onEvent: (Nothing) -> Unit,
        onNavEvent: (LandingNavEvent) -> Unit,
    ) {
        LandingScreen(onNavEvent = onNavEvent)
    }
}