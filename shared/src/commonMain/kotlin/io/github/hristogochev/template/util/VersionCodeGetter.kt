package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberVersionCodeGetter(): VersionCodeGetter

interface VersionCodeGetter {
    fun getVersionCode(): ULong?
}