package io.github.hristogochev.template.util

import android.net.Uri
import androidx.core.net.toUri
import kotlin.runCatching

fun String.toUriOrNull(): Uri? {
    return runCatching {
        this.toUri()
    }.getOrNull()
}