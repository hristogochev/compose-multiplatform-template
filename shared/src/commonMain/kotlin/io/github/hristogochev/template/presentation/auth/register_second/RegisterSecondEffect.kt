package io.github.hristogochev.template.presentation.auth.register_second

sealed interface RegisterSecondEffect {
    sealed interface Failure : RegisterSecondEffect {
        data object ConnectionError : Failure
        data object Failed : Failure
        data class Other(val reason: String) : Failure
    }

    data object TooManyRequests : RegisterSecondEffect
    data object TryAgainLater : RegisterSecondEffect
    data object FocusTextField : RegisterSecondEffect
}