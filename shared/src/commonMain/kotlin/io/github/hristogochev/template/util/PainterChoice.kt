package io.github.hristogochev.template.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import io.github.hristogochev.template.presentation.theme.primary


sealed interface PainterChoice {
    val color: Color

    data class ImageVector(
        val imageVector: androidx.compose.ui.graphics.vector.ImageVector,
        override val color: Color = primary,
        val alpha: Float = 1f,
    ) : PainterChoice

    data class Painter(
        val painter: androidx.compose.ui.graphics.painter.Painter,
        override val color: Color = primary,
        val alpha: Float = 1f,
        val width: Dp? = null,
        val height: Dp? = null,
    ) : PainterChoice
}