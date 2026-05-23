package io.github.hristogochev.template.presentation.component.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.hristogochev.template.presentation.theme.primary

enum class CommonTextButtonMode(
    val contentColor: Color,
    val alpha: Float,
    val contentPadding: PaddingValues,
    val fontSize: TextUnit = 16.sp,
    val fontWeight: FontWeight = FontWeight.Normal
) {
    Primary(
        contentColor = primary,
        alpha = 1f,
        contentPadding = PaddingValues(horizontal = 8.dp)
    ),
}

@Composable
fun CommonTextButton(
    text: String,
    mode: CommonTextButtonMode = CommonTextButtonMode.Primary,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth().padding(mode.contentPadding),
        contentAlignment = Alignment.Center
    ) {
        CommonText(
            modifier = Modifier.let {
                if (enabled) {
                    it.clickable {
                        onClick()
                    }
                } else it
            },
            color = mode.contentColor,
            alpha = if (enabled) 1f else 0.5f,
            text = text,
            fontSize = mode.fontSize,
            fontWeight = mode.fontWeight,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}
