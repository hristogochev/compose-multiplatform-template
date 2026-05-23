package io.github.hristogochev.template.presentation.component.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.hristogochev.template.presentation.theme.background
import io.github.hristogochev.template.presentation.theme.primary
import io.github.hristogochev.template.presentation.theme.surfaceBright


enum class CommonButtonMode(
    val containerColor: Color,
    val contentColor: Color,
    val contentPadding: PaddingValues,
    val disabledContainerColor: Color = containerColor.copy(alpha = 0.5f),
    val disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    val borderStroke: BorderStroke? = null,
    val disabledBorderStroke: BorderStroke? = borderStroke,
    val shape: Shape = RoundedCornerShape(100.dp),
    val fontSize: TextUnit = 16.sp,
    val fontWeight: FontWeight = FontWeight.Normal
) {
    Primary(
        containerColor = primary,
        contentColor = background,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 32.dp),
    ),
    Inverse(
        containerColor = background,
        contentColor = primary,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 32.dp),
    ),
    Decline(
        containerColor = Color.White,
        contentColor = primary,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
    ),
    Accept(
        containerColor = primary,
        contentColor = surfaceBright,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
    ),
    LongAccept(
        containerColor = primary,
        contentColor = background,
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 42.dp),
    ),
}

@Composable
fun CommonButton(
    text: String,
    mode: CommonButtonMode = CommonButtonMode.Primary,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = mode.containerColor,
            contentColor = mode.contentColor,
            disabledContainerColor = mode.disabledContainerColor,
            disabledContentColor = mode.disabledContentColor
        ),
        border = if (enabled) mode.borderStroke else mode.disabledBorderStroke,
        contentPadding = mode.contentPadding,
        shape = mode.shape,
    ) {
        Text(
            text = text,
            maxLines = 1,
            textAlign = TextAlign.Center,
            letterSpacing = 0.sp,
            lineHeight = mode.fontSize * 1.3,
            fontSize = mode.fontSize,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
        )
    }
}


