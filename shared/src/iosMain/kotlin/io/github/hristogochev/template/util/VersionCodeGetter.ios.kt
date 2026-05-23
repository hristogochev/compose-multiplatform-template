package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSBundle

@Composable
actual fun rememberVersionCodeGetter(): VersionCodeGetter {
    return remember() {
        IOSVersionCodeGetter()
    }
}

class IOSVersionCodeGetter : VersionCodeGetter {
    override fun getVersionCode(): ULong? {
        val cfBundleVersion =
            NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleVersion") ?: return null
        val stringVersion = cfBundleVersion as? String ?: return null
        return stringVersion.toULongOrNull()
    }
}