package io.github.hristogochev.template.util.riptide

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import io.github.hristogochev.template.util.IgnoredOnParcel
import io.github.hristogochev.vortex.navigator.Navigator
import io.github.hristogochev.vortex.screen.CurrentScreen
import io.github.hristogochev.vortex.transitions.FadeTransition

abstract class TabXNav<InitialScreenState> : TabX {

    @IgnoredOnParcel
    override var navigator: Navigator? = null

    @Composable
    override fun Content(
        innerPadding: PaddingValues,
        snackBarHostState: SnackbarHostState,
        setFullScreen: (Boolean) -> Unit,
    ) {
        Navigator(
            initialScreens()
        ) {
            LaunchedEffect(it) {
                navigator = it
            }
            CurrentScreen(it, FadeTransition, FadeTransition) { screen ->
                (screen as ScreenX).Content(
                    innerPadding,
                    snackBarHostState,
                    setFullScreen = setFullScreen
                )
            }
        }
    }

    @Composable
    abstract fun initialScreens(): List<ScreenX>
}