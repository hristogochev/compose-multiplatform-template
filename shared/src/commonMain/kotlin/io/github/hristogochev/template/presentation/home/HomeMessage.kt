package io.github.hristogochev.template.presentation.home

sealed interface HomeMessage {
    data object ConnectionError : HomeMessage
    data object SomethingWentWrong : HomeMessage
}