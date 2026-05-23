package io.github.hristogochev.template.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

@Composable
actual fun ChangeTopAndBottomBars(
    isAppearanceLightStatusBars: Boolean,
    isAppearanceLightNavigationBars: Boolean,
) {
    LaunchedEffect(isAppearanceLightStatusBars) {
        val statusBarStyle =
            if (isAppearanceLightStatusBars) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent
        UIApplication.sharedApplication.setStatusBarStyle(statusBarStyle)
    }
}


@Composable
actual fun ChangeStatusBars(isAppearanceLightStatusBars: Boolean) {
    LaunchedEffect(isAppearanceLightStatusBars) {
        val statusBarStyle =
            if (isAppearanceLightStatusBars) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent
        UIApplication.sharedApplication.setStatusBarStyle(statusBarStyle)
    }
}

@Composable
actual fun ChangeNavigationBars(isAppearanceLightNavigationBars: Boolean) {

}