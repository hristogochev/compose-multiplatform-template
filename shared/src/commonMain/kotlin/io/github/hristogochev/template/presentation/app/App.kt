package io.github.hristogochev.template.presentation.app

import androidx.compose.runtime.Composable
import io.github.hristogochev.template.presentation.main.MainScreen
import io.github.hristogochev.template.presentation.splash.SplashScreen
import io.github.hristogochev.template.presentation.theme.TemplateTheme

@Composable
fun App(prepared: AppPrepared) {
    TemplateTheme {
        when (prepared) {
            is AppPrepared.Prepared -> {
                MainScreen(prepared)
            }

            AppPrepared.Preparing -> {
                SplashScreen()
            }
        }
    }
}

