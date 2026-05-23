package io.github.hristogochev.template.presentation.main

sealed interface MainEvent {
    data object HideLoggingOutSheet : MainEvent
    data object ShowLoggingOutSheet : MainEvent
    data class NotificationDialogDismiss(val id: Int) : MainEvent
}