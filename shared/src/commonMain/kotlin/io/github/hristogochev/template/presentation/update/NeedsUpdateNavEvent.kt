package io.github.hristogochev.template.presentation.update

sealed interface NeedsUpdateNavEvent {
    data class Update(val iOSAppId: String) : NeedsUpdateNavEvent
    data class Skip(val skippedVersion: ULong) : NeedsUpdateNavEvent
}