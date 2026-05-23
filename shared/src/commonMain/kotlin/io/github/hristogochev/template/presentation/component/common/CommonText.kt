package io.github.hristogochev.template.presentation.component.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CommonText(
    fontSize: TextUnit,
    fontWeight: FontWeight,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    alpha: Float = 1f,
    textAlign: TextAlign = TextAlign.Start,
    header: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
    lineHeight: TextUnit = fontSize * 1.3,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color.copy(alpha = alpha),
        fontWeight = fontWeight,
        fontFamily = if (header) MaterialTheme.typography.headlineLarge.fontFamily else MaterialTheme.typography.bodyLarge.fontFamily,
        fontSize = fontSize,
        letterSpacing = 0.sp,
        lineHeight = lineHeight,
        textAlign = textAlign,
        overflow = overflow,
        maxLines = maxLines
    )
}

@Composable
fun CommonText(
    fontSize: TextUnit,
    fontWeight: FontWeight,
    text: AnnotatedString,
    color: Color = MaterialTheme.colorScheme.onBackground,
    alpha: Float = 1f,
    textAlign: TextAlign = TextAlign.Start,
    header: Boolean = false,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        color = color.copy(alpha = alpha),
        fontWeight = fontWeight,
        fontFamily = if (header) MaterialTheme.typography.headlineLarge.fontFamily else MaterialTheme.typography.bodyLarge.fontFamily,
        fontSize = fontSize,
        maxLines = maxLines,
        letterSpacing = 0.sp,
        lineHeight = fontSize * 1.3,
        textAlign = textAlign,
        overflow = overflow
    )
}

