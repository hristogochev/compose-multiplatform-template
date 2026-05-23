package io.github.hristogochev.template.util

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberStoreOpener(): StoreOpener {
    val context = LocalContext.current
    return remember(context) {
        AndroidStoreOpener(context)
    }
}

class AndroidStoreOpener(private val context: Context) : StoreOpener {
    override fun openStore(iOSAppId: String): Boolean {
        val applicationId = context.packageName ?: return false
        val uri = "https://play.google.com/store/apps/details?id=$applicationId".toUriOrNull()
            ?: return false
        val intent = Intent(Intent.ACTION_VIEW).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = uri
            setPackage("com.android.vending")
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
        return true
    }
}