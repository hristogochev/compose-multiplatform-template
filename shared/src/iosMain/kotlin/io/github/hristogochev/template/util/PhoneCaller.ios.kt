package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun rememberPhoneCaller(): PhoneCaller {
    return remember {
        IOSPhoneCaller()
    }
}

class IOSPhoneCaller : PhoneCaller {
    override fun initiateCall(phoneNumber: String): Boolean {
        val url = "tel:${phoneNumber}"
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl != null && UIApplication.sharedApplication.canOpenURL(nsUrl)) {
            UIApplication.sharedApplication.openURL(nsUrl)
        }
        return true
    }
}