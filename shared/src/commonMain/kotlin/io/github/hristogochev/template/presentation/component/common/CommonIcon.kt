package io.github.hristogochev.template.presentation.component.common

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.hristogochev.template.util.PainterChoice


@Composable
fun CommonIcon(
    painter: PainterChoice,
    contentDescription: String?,
    tint: Color = LocalContentColor.current,
    modifier: Modifier = Modifier,
) {
    when (painter) {
        is PainterChoice.ImageVector -> {
            Icon(
                modifier = modifier,
                imageVector = painter.imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }

        is PainterChoice.Painter -> {
            Icon(
                modifier = modifier,
                painter = painter.painter,
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}