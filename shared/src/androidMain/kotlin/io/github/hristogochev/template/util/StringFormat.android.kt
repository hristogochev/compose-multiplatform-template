package io.github.hristogochev.template.util

actual fun stringFormat(format: String, value: Int) = String.format(format, value)
actual fun stringFormat(format: String, value: UInt) = String.format(format, value.toLong())
actual fun stringFormat(format: String, value: ULong) = String.format(format, value)
actual fun stringFormat(format: String, value: Double) = String.format(format, value)
