package io.github.hristogochev.template.presentation.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import io.github.hristogochev.template.presentation.app.AppPrepared
import io.github.hristogochev.template.presentation.auth.landing.LandingScreenNav
import io.github.hristogochev.template.presentation.auth.register_success.RegisterSuccessScreenNav
import io.github.hristogochev.template.presentation.home.HomeScreenNav
import io.github.hristogochev.template.util.consumeEffect
import io.github.hristogochev.template.util.consumeNavEffect
import io.github.hristogochev.template.util.rememberStoreOpener
import io.github.hristogochev.vortex.koin.koinNavigatorScreenModel
import io.github.hristogochev.vortex.navigator.Navigator
import io.github.hristogochev.vortex.screen.CurrentScreen
import io.github.hristogochev.vortex.transitions.FadeTransition
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import cmptemplate2.shared.generated.resources.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(prepared: AppPrepared.Prepared) {
    Navigator(
        screens = when (prepared) {
            is AppPrepared.Prepared.Landing -> {
                listOf(LandingScreenNav)
            }

            is AppPrepared.Prepared.Home -> {
                val additionalState = prepared.payloadData?.let {}
                listOfNotNull(HomeScreenNav(prepared.homeState))
            }
        }
    ) { navigator ->
        val initialState = rememberSaveable {
            MainState(prepared)
        }

        val viewModel =
            navigator.koinNavigatorScreenModel<MainViewModel> { parametersOf(initialState) }

        val state by viewModel.state.collectAsState()
        val effect by viewModel.effect.collectAsState()
        val navEffect by viewModel.navEffect.collectAsState()
        val onEvent = viewModel::onEvent
        val onNavEvent = viewModel::onNavEvent

        consumeEffect(effect) {

        }

        consumeNavEffect(navEffect) {
            when (it) {
                is MainNavEffect.Landing -> {
                    navigator.replaceAll(LandingScreenNav)
                }
            }
        }

        val storeOpener = rememberStoreOpener()

        val needsUpdate = state.needsUpdate
        if (needsUpdate != null) {
//            InfoDialog(
//                header = "Остаряла версия",
//                body = "Моля, актуализирай приложението си, за да продължиш."
//            ) {
//                storeOpener.openStore(needsUpdate.iOSAppId)
//            }
        }

        if (state.sessionExpired) {

        }


        state.notificationDialogs.forEach { notificationDialog ->

        }

//        ConfirmBottomSheet(
//            isOpen = if (state.loggingOut) Unit else null,
//            text = stringResource(Res.string.profile_1_9),
//            declineText = stringResource(Res.string.profile_1_10),
//            acceptText = stringResource(Res.string.profile_1_11),
//            onAccept = {
//                onNavEvent(MainNavEvent.Logout)
//            },
//            onDismissRequest = {
//                onEvent(MainEvent.HideLoggingOutSheet)
//            },
//        )

        CurrentScreen(navigator, FadeTransition, FadeTransition)
    }
}

