package io.github.hristogochev.template.util

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class FadeSide {
    START, END, BOTTOM, TOP
}

fun Size.getFadeOffsets(side: FadeSide): Pair<Offset, Offset> {
    return when (side) {
        FadeSide.START -> Offset.Zero to Offset(width, 0f)
        FadeSide.END -> Offset(width, 0f) to Offset.Zero
        FadeSide.BOTTOM -> Offset(0f, height) to Offset.Zero
        FadeSide.TOP -> Offset.Zero to Offset(0f, height)
    }
}

fun Modifier.fadingEdge(
    vararg sides: FadeSide,
    color: Color,
    width: Dp = 16.dp,
    isVisible: Boolean,
    spec: AnimationSpec<Dp>? = null
) = composed {
    require(width > 0.dp) { "Invalid fade width: Width must be greater than 0" }

    val animatedWidth = spec?.let {
        animateDpAsState(
            targetValue = if (isVisible) width else 0.dp,
            animationSpec = spec,
            label = "Fade width"
        ).value
    }

    drawWithContent {
        // Draw the content
        this@drawWithContent.drawContent()

        // Go through all provided sides
        sides.forEach { side ->
            // Get start and end gradient offsets
            val (start, end) = this.size.getFadeOffsets(side)

            // Define the static width
            val staticWidth = if (isVisible) width.toPx() else 0f
            // Define the final width
            val widthPx = animatedWidth?.toPx() ?: staticWidth

            // Calculate fraction based on view size
            val fraction = when (side) {
                FadeSide.START, FadeSide.END -> widthPx / this.size.width
                FadeSide.BOTTOM, FadeSide.TOP -> widthPx / this.size.height
            }

            // Draw the gradient
            drawRect(
                brush = Brush.linearGradient(
                    0f to color,
                    fraction to Color.Transparent,
                    start = start,
                    end = end
                ),
                size = this.size
            )
        }
    }
}

fun Modifier.topFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    width: Dp = 16.dp,
    spec: AnimationSpec<Dp>? = tween(250)
) = fadingEdge(FadeSide.TOP, color = color, width = width, isVisible = isVisible, spec = spec)

fun Modifier.startFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    width: Dp = 16.dp,
    spec: AnimationSpec<Dp>? = tween(250)
) = fadingEdge(FadeSide.START, color = color, width = width, isVisible = isVisible, spec = spec)

fun Modifier.endFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    width: Dp = 16.dp,
    spec: AnimationSpec<Dp>? = tween(250)
) = fadingEdge(FadeSide.END, color = color, width = width, isVisible = isVisible, spec = spec)

fun Modifier.bottomFadingEdge(
    color: Color,
    isVisible: Boolean = true,
    width: Dp = 16.dp,
    spec: AnimationSpec<Dp>? = tween(250)
) = fadingEdge(FadeSide.BOTTOM, color = color, width = width, isVisible = isVisible, spec = spec)