package io.github.hristogochev.template.presentation.auth.login_second

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import cmptemplate2.shared.generated.resources.*
import io.github.hristogochev.template.util.ChangeTopAndBottomBars
import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.consumeEffect
import io.github.hristogochev.template.util.iOSKeyboardCloseable
import org.jetbrains.compose.resources.getString


@Composable
fun LoginSecondScreen(
    state: LoginSecondState,
    onEvent: (LoginSecondEvent) -> Unit,
    effect: Effect<LoginSecondEffect>?,
    onNavEvent: (LoginSecondNavEvent) -> Unit,
) {
    ChangeTopAndBottomBars(
        isAppearanceLightStatusBars = true,
        isAppearanceLightNavigationBars = true
    )

    val snackBarHostState = remember { SnackbarHostState() }

    val focusRequester = remember { FocusRequester() }

    consumeEffect(effect) {
        when (it) {
            LoginSecondEffect.Failure.ConnectionError -> {
                snackBarHostState.showSnackbar(getString(Res.string.connection_error))
            }

            LoginSecondEffect.Failure.Failed -> {
                snackBarHostState.showSnackbar(getString(Res.string.something_went_wrong))
            }

            is LoginSecondEffect.Failure.Other -> {
                snackBarHostState.showSnackbar(it.reason)
            }

            LoginSecondEffect.TooManyRequests -> snackBarHostState.showSnackbar(getString(Res.string.slow_down))
            LoginSecondEffect.TryAgainLater -> snackBarHostState.showSnackbar(getString(Res.string.try_again_later))
            LoginSecondEffect.FocusTextField -> focusRequester.requestFocus()
        }
    }

    var otpKeyboardShown by remember {
        mutableStateOf(false)
    }


    val keyboardShown by remember(
        otpKeyboardShown
    ) {
        derivedStateOf {
            otpKeyboardShown
        }
    }


    Scaffold(
        modifier = Modifier.iOSKeyboardCloseable(keyboardShown),
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

