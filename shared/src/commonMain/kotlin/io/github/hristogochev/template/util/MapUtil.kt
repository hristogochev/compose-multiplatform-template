package io.github.hristogochev.template.util

fun <T, C : Comparable<C>> Map<C, List<T>>.categorizeWithIndexesSticky(): Map<Int, C> {
    val items = this

    val categories = items.keys.toList().sorted()

    var virtualIndex = 2

    val map = mutableMapOf<Int, C>()

    for (category in categories) {
        map[virtualIndex] = category
        val size = items[category].orEmpty().size
        virtualIndex += 1 + size
    }

    return map.toMap()
}


fun <K, V, R> Map<K, V>.mapNotNullKeys(transform: (K) -> R?): Map<R, V> {
    return this.mapNotNull { (key, value) ->
        transform(key)?.let { transformedKey ->
            transformedKey to value
        }
    }.toMap()
}

fun <K, V, R> Map<K, V>.mapNotNullValues(transform: (V) -> R?): Map<K, R> {
    return this.mapNotNull { (key, value) ->
        transform(value)?.let { transformedValue ->
            key to transformedValue
        }
    }.toMap()
}


fun <K : Any, V> Map<K?, V>.filterNotNullKeys(): Map<K, V> {
    val result = mutableMapOf<K, V>()
    for ((key, value) in this) {
        if (key != null) {
            result[key] = value
        }
    }
    return result
}

fun <K, V : Any> Map<K, V?>.filterNotNullValues(): Map<K, V> {
    val result = mutableMapOf<K, V>()
    for ((key, value) in this) {
        if (value != null) {
            result[key] = value
        }
    }
    return result
}