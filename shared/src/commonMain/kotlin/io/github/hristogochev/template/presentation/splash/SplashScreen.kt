package io.github.hristogochev.template.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.hristogochev.template.util.ChangeTopAndBottomBars


@Composable
fun SplashScreen() {

    ChangeTopAndBottomBars(
        isAppearanceLightStatusBars = false,
        isAppearanceLightNavigationBars = true,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp)
            .padding(top = 64.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

    }
}