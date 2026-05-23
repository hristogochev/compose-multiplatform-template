package io.github.hristogochev.template.util

fun matchPhoneNumber(phoneNumber: String): Boolean {
    val phoneRegex = Regex(
        "(\\+[0-9]+[\\- .]*)?" +
                "(\\([0-9]+\\)[\\- .]*)?" +
                "([0-9][0-9\\- .]+[0-9])"
    )
    return phoneRegex.matches(phoneNumber)
}