package io.github.hristogochev.template.presentation.component.common

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CommonClickableText(
    modifier: Modifier = Modifier,
    wholeText: String,
    clickablePart: String,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Light,
    clickableFontSize: TextUnit = fontSize,
    clickableFontWeight: FontWeight = FontWeight.Normal,
    color: Color = MaterialTheme.colorScheme.onBackground,
    clickableColor: Color = MaterialTheme.colorScheme.primary,
    clickableTextDecoration: TextDecoration = TextDecoration.Underline,
    onClick: () -> Unit,
) {

    val annotatedString = buildAnnotatedString {
        append(wholeText)

        val start = wholeText.indexOf(clickablePart)
        val end = start + clickablePart.length

        addStyle(
            style = SpanStyle(
                color = clickableColor,
                textDecoration = clickableTextDecoration,
                fontWeight = clickableFontWeight,
                fontSize = clickableFontSize
            ), start = start, end = end
        )

        addStringAnnotation("clickable", "clickable", start, end)
    }
    ClickableText(
        text = annotatedString,
        modifier = modifier, onClick = { offset ->
            val uri =
                annotatedString.getStringAnnotations("clickable", offset, offset)
                    .firstOrNull()?.item

            if (uri != null) {
                onClick()
            }
        }, style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            fontWeight = fontWeight,
            textAlign = textAlign,
            lineHeight = fontSize * 1.3,
            letterSpacing = 0.sp,
        )
    )
}

