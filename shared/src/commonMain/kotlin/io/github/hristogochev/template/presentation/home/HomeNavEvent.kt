package io.github.hristogochev.template.presentation.home


sealed interface HomeNavEvent {
    data object OnLogout : HomeNavEvent
}