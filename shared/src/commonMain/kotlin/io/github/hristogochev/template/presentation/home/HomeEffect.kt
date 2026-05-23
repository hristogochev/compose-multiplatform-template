package io.github.hristogochev.template.presentation.home

sealed interface HomeEffect {
    data object SomethingWentWrong : HomeEffect
    data object ConnectionError : HomeEffect
}