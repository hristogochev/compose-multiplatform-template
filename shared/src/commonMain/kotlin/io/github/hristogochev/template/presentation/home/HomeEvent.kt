package io.github.hristogochev.template.presentation.home

sealed interface HomeEvent {
    data class OnPostNotificationPermission(val granted: Boolean) : HomeEvent
}