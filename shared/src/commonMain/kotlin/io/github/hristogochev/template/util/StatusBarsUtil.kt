package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable


@Composable
expect fun ChangeTopAndBottomBars(
    isAppearanceLightStatusBars: Boolean,
    isAppearanceLightNavigationBars: Boolean,
)

@Composable
expect fun ChangeStatusBars(isAppearanceLightStatusBars: Boolean)

@Composable
expect fun ChangeNavigationBars(isAppearanceLightNavigationBars: Boolean)
