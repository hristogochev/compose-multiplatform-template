package io.github.hristogochev.template.presentation.auth.landing

sealed interface LandingNavEvent {
    data object Register : LandingNavEvent
    data object Login : LandingNavEvent
}