package io.github.hristogochev.template.util

fun Int.format2Letter() = stringFormat("%02d", this)
fun UInt.format2Letter() = stringFormat("%02d", this)
fun ULong.format2Letter(): String {
    val zero: ULong = 0u
    if (this == zero) {
        return "0"
    }
    return this.toString().padStart(2, '0')
//    return stringFormat("%02d", this)
}
