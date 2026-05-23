package io.github.hristogochev.template.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

val defaultTypography = Typography()

@Composable
fun TemplateTypography(): Typography {
    val default = fontDefault()
    return Typography(
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = default),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = default),
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = default),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = default),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = default),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = default),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = default),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = default),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = default),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = default),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = default),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = default),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = default),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = default),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = default),
    )
}