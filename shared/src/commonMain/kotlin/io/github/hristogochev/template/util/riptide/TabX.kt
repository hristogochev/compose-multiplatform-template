package io.github.hristogochev.template.util.riptide

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import io.github.hristogochev.vortex.navigator.Navigator
import io.github.hristogochev.vortex.tab.Tab


interface TabX : Tab {
    var navigator: Navigator?

    @Composable
    fun Content(
        innerPadding: PaddingValues,
        snackBarHostState: SnackbarHostState,
        setFullScreen: (Boolean) -> Unit,
    )

    @Composable
    override fun Content() {
        error("Called Content without arguments")
    }
}