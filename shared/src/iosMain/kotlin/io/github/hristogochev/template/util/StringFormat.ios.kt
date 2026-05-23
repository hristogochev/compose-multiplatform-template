package io.github.hristogochev.template.util

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun stringFormat(format: String, value: Int): String =
    NSString.stringWithFormat(format, value)

actual fun stringFormat(format: String, value: UInt): String =
    NSString.stringWithFormat(format, value.toLong())

actual fun stringFormat(format: String, value: ULong): String =
    NSString.stringWithFormat(format, value)

actual fun stringFormat(format: String, value: Double): String =
    NSString.stringWithFormat(format, value)
