package io.github.hristogochev.template.util.notification

import io.github.hristogochev.template.util.filterNotNullValues

internal fun Map<Any?, *>.asPayloadData(): Map<String, String> {
    return this.keys
        .filterNotNull()
        .filterIsInstance<String>()
        .associateWith { key -> this[key]?.toString() }
        .filterNotNullValues()
}

