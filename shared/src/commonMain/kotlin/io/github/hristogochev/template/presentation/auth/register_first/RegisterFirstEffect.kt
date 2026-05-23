package io.github.hristogochev.template.presentation.auth.register_first

sealed interface RegisterFirstEffect {
    sealed interface Failure : RegisterFirstEffect {
        data object ConnectionError : Failure
        data object Failed : Failure
        data class Other(val reason: String) : Failure
    }

    data object TooManyRequests : RegisterFirstEffect
    data object FocusTextField : RegisterFirstEffect
}