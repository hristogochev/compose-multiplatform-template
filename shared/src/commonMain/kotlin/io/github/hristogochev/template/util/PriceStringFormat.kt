package io.github.hristogochev.template.util

fun Double.priceStringFormat() = stringFormat("%.2f", this).replace(",", ".")
fun Double.ratingStringFormat() = stringFormat("%.1f", this).replace(',', '.')