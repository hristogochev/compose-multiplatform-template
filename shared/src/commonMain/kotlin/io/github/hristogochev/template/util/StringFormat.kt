package io.github.hristogochev.template.util

expect fun stringFormat(format: String, value: Int): String
expect fun stringFormat(format: String, value: UInt): String
expect fun stringFormat(format: String, value: ULong): String
expect fun stringFormat(format: String, value: Double): String

