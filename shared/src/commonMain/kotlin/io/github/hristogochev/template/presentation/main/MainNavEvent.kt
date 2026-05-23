package io.github.hristogochev.template.presentation.main


sealed interface MainNavEvent {
    data object HideSessionExpiredDialog : MainNavEvent

    data object Logout : MainNavEvent
    data class NotificationDialogClicked(
        val id: Int,
        val data: Map<String, String>,
    ) : MainNavEvent
}