package io.github.hristogochev.template.util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun ChangeTopAndBottomBars(
    isAppearanceLightStatusBars: Boolean,
    isAppearanceLightNavigationBars: Boolean
) {
    val view = LocalView.current

    val insetsController by remember(view) {
        derivedStateOf {
            if (view.isInEditMode) return@derivedStateOf null
            val window = (view.context as Activity).window ?: return@derivedStateOf null
            WindowCompat.getInsetsController(window, view)
        }
    }

    LaunchedEffect(isAppearanceLightStatusBars, insetsController) {
        insetsController?.isAppearanceLightStatusBars = isAppearanceLightStatusBars
    }

    LaunchedEffect(isAppearanceLightNavigationBars, insetsController) {
        insetsController?.isAppearanceLightNavigationBars = isAppearanceLightNavigationBars
    }
}

@Composable
actual fun ChangeStatusBars(isAppearanceLightStatusBars: Boolean) {
    val view = LocalView.current

    val insetsController by remember(view) {
        derivedStateOf {
            if (view.isInEditMode) return@derivedStateOf null
            val window = (view.context as Activity).window ?: return@derivedStateOf null
            WindowCompat.getInsetsController(window, view)
        }
    }

    LaunchedEffect(isAppearanceLightStatusBars, insetsController) {
        insetsController?.isAppearanceLightStatusBars = isAppearanceLightStatusBars
    }
}

@Composable
actual fun ChangeNavigationBars(isAppearanceLightNavigationBars: Boolean) {
    val view = LocalView.current

    val insetsController by remember(view) {
        derivedStateOf {
            if (view.isInEditMode) return@derivedStateOf null
            val window = (view.context as Activity).window ?: return@derivedStateOf null
            WindowCompat.getInsetsController(window, view)
        }
    }

    LaunchedEffect(isAppearanceLightNavigationBars, insetsController) {
        insetsController?.isAppearanceLightNavigationBars = isAppearanceLightNavigationBars
    }
}

