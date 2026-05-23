package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun rememberStoreOpener(): StoreOpener {
    return remember {
        IOSStoreOpener()
    }
}

class IOSStoreOpener : StoreOpener {
    override fun openStore(iOSAppId: String): Boolean {
        val url = NSURL.URLWithString("https://apps.apple.com/app/id$iOSAppId") ?: return false
        if (!UIApplication.sharedApplication.canOpenURL(url)) {
            return false
        }
        return UIApplication.sharedApplication.openURL(url)
    }
}