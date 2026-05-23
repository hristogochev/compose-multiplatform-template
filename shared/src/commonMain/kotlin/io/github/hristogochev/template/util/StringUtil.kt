package io.github.hristogochev.template.util

fun String.isNotPureEmpty(): Boolean {
    return this.trimStart().isNotEmpty()
}