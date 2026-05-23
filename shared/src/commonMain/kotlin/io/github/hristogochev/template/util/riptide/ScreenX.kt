package io.github.hristogochev.template.util.riptide

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.hristogochev.vortex.screen.Screen


interface ScreenX : Screen {

    @Composable
    fun Content(
        innerPadding: PaddingValues,
        snackBarHostState: SnackbarHostState,
        setFullScreen: (Boolean) -> Unit,
    )

    @Composable
    override fun Content() {
        println("Called Content without arguments")

        val snackBarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = { SnackbarHost(snackBarHostState) },
            content = { innerPadding ->
                Content(
                    innerPadding = innerPadding,
                    snackBarHostState = snackBarHostState,
                    setFullScreen = {}
                )
            },
        )
    }
}
