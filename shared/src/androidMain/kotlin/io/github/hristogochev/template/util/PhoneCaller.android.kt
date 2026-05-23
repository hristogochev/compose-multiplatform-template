package io.github.hristogochev.template.util

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberPhoneCaller(): PhoneCaller {
    val context = LocalContext.current

    return remember(context) {
        AndroidPhoneCaller(context)
    }
}

class AndroidPhoneCaller(private val context: Context) : PhoneCaller {
    override fun initiateCall(phoneNumber: String): Boolean {
        val uri = "tel:${phoneNumber}".toUriOrNull() ?: return false
        val intent = Intent(Intent.ACTION_DIAL, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
        return true
    }
}