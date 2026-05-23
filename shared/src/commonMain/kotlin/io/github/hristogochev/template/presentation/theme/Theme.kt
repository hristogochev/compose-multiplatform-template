package io.github.hristogochev.template.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val primary = Color(0xFFAC231A)
val background = Color(0xFFEBEBEB)
val onBackground = Color(0xFF272725)
val surface = Color(0xFFE2E2E2)
val surfaceBright = Color(0xFFF8F8F8)
val surfaceVariant = Color(0xFFF2F2F2)
val secondary = Color(0xFF118222)
val tertiary = Color(0xFF003E09)


@Composable
fun TemplateTheme(
    content: @Composable () -> Unit
) {
    val colors = lightColorScheme(
        primary = primary,
        background = background,
        onBackground = onBackground,
        surface = surface,
        surfaceBright = surfaceBright,
        surfaceVariant = surfaceVariant,
        secondary = secondary,
        tertiary = tertiary,
    )
    MaterialTheme(
        typography = TemplateTypography(),
        colorScheme = colors,
        content = content
    )
}

