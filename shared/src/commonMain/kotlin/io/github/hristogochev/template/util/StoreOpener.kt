package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberStoreOpener(): StoreOpener

interface StoreOpener {
    fun openStore(iOSAppId: String): Boolean
}