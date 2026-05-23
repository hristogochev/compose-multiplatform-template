package io.github.hristogochev.template.presentation.auth.login_first

sealed interface LoginFirstEffect {
    sealed interface Failure : LoginFirstEffect {
        data object ConnectionError : Failure
        data object Failed : Failure
        data class Other(val reason: String) : Failure
    }

    data object TooManyRequests : LoginFirstEffect
    data object FocusTextField : LoginFirstEffect
}