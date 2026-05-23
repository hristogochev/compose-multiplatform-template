package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable

@Composable
expect fun rememberPhoneCaller(): PhoneCaller

interface PhoneCaller {
    fun initiateCall(phoneNumber: String): Boolean
}