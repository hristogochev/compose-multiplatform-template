package io.github.hristogochev.template.presentation.auth.register_first

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
fun RegisterFirstScreen(
    state: RegisterFirstState,
    onEvent: (RegisterFirstEvent) -> Unit,
    effect: Effect<RegisterFirstEffect>?,
    onNavEvent: (RegisterFirstNavEvent) -> Unit,
) {

    ChangeTopAndBottomBars(
        isAppearanceLightStatusBars = true,
        isAppearanceLightNavigationBars = true
    )

    val snackBarHostState = remember { SnackbarHostState() }

    val focusRequester = remember { FocusRequester() }

    consumeEffect(effect) {
        when (it) {
            RegisterFirstEffect.Failure.ConnectionError -> {
                snackBarHostState.showSnackbar(getString(Res.string.connection_error))
            }

            RegisterFirstEffect.Failure.Failed -> {
                snackBarHostState.showSnackbar(getString(Res.string.something_went_wrong))
            }

            is RegisterFirstEffect.Failure.Other -> {
                snackBarHostState.showSnackbar(it.reason)
            }

            RegisterFirstEffect.TooManyRequests -> snackBarHostState.showSnackbar(getString(Res.string.slow_down))

            RegisterFirstEffect.FocusTextField -> focusRequester.requestFocus()
        }
    }

    var phoneNumberKeyboardShown by remember {
        mutableStateOf(false)
    }

    val keyboardShown by remember(
        phoneNumberKeyboardShown
    ) {
        derivedStateOf {
            phoneNumberKeyboardShown
        }
    }

    Scaffold(
        modifier = Modifier.iOSKeyboardCloseable(keyboardShown),
        snackbarHost = { SnackbarHost(snackBarHostState) })
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 56.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}



