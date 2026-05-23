package io.github.hristogochev.template.util

import android.os.Bundle
import kotlin.collections.associateWith
import kotlin.collections.filterValues

fun Bundle.toMap(): Map<String, *> {
    return this.keySet().associateWith { key ->
        @Suppress("DEPRECATION")
        this.get(key)
    }.filterValues { it != null }
}