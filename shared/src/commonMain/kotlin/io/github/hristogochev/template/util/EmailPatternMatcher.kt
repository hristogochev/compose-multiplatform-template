package io.github.hristogochev.template.util

fun matchEmail(email: String): Boolean {
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
    return email.matches(emailRegex.toRegex())
}