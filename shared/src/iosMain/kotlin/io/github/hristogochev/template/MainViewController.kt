package io.github.hristogochev.template

import androidx.compose.ui.window.ComposeUIViewController
import io.github.hristogochev.template.presentation.app.App
import io.github.hristogochev.template.presentation.app.AppPrepared

@Suppress("unused", "FunctionName")
fun MainViewController(prepared: AppPrepared) = ComposeUIViewController { App(prepared) }
