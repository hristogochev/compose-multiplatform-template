package io.github.hristogochev.template.util

import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.toSize

@Composable
fun windowSize(): DpSize {
    return with(LocalDensity.current) { currentWindowSize().toSize().toDpSize() }
}
