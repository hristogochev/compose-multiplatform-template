package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberAppSettingsOpener(): AppSettingsOpener

interface AppSettingsOpener {
    fun open(): Boolean
}
