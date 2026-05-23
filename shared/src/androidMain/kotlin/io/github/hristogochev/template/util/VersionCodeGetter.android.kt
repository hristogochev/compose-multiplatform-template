package io.github.hristogochev.template.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberVersionCodeGetter(): VersionCodeGetter {
    val context = LocalContext.current
    return remember(context) {
        AndroidVersionCodeGetter(context)
    }
}

class AndroidVersionCodeGetter(private val context: Context) : VersionCodeGetter {
    override fun getVersionCode(): ULong? {
        val packageManager = context.packageManager ?: return null
        val packageName = context.packageName ?: return null
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0) ?: return null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toULong()
            } else {
                @Suppress("DEPRECATION") packageInfo.versionCode.toULong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return null
        }
    }
}
