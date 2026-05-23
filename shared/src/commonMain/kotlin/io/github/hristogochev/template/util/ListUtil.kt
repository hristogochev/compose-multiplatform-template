package io.github.hristogochev.template.util

fun MutableList<String>.removeConsecutiveDuplicates() {
    if (this.isEmpty()) return

    val iterator = this.listIterator()
    var previous: String? = null

    while (iterator.hasNext()) {
        val current = iterator.next()
        if (current == previous) {
            iterator.remove()
        } else {
            previous = current
        }
    }
}

fun <T> List<T>.takeUntilInclusive(predicate: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    for (item in this) {
        result.add(item)
        if (predicate(item)) break
    }
    return result
}