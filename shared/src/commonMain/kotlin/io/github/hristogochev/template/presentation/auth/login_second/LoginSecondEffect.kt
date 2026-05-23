package io.github.hristogochev.template.presentation.auth.login_second

sealed interface LoginSecondEffect {
    sealed interface Failure : LoginSecondEffect {
        data object ConnectionError : Failure
        data object Failed : Failure
        data class Other(val reason: String) : Failure
    }

    data object TooManyRequests : LoginSecondEffect
    data object TryAgainLater : LoginSecondEffect
    data object FocusTextField : LoginSecondEffect
}