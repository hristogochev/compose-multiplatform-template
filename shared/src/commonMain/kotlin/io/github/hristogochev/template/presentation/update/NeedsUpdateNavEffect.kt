package io.github.hristogochev.template.presentation.update

sealed interface NeedsUpdateNavEffect {
    data object Skip : NeedsUpdateNavEffect
}