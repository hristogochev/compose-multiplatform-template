package io.github.hristogochev.template.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import cmptemplate2.shared.generated.resources.*
import io.github.hristogochev.perk.permission.Permission
import io.github.hristogochev.perk.permission.PermissionStatus
import io.github.hristogochev.perk.permission.rememberPermissionRequestDialogLauncher
import io.github.hristogochev.perk.permission.rememberPermissionRequestLauncher
import io.github.hristogochev.template.domain.repository.FirstLoginNotificationPermissionRequestedRepository
import io.github.hristogochev.template.util.Effect
import io.github.hristogochev.template.util.consumeEffect
import io.github.hristogochev.vortex.navigator.Navigator
import io.github.hristogochev.vortex.tab.Tab
import org.jetbrains.compose.resources.getString
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    tabs: List<Tab>,
    tabNavigator: Navigator,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    effect: Effect<HomeEffect>?,
    onNavEvent: (HomeNavEvent) -> Unit,
) {

    val snackBarHostState = remember { SnackbarHostState() }

    val (fullScreen, setFullScreen) = rememberSaveable {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    consumeEffect(effect) {
        when (it) {
            HomeEffect.ConnectionError -> snackBarHostState.showSnackbar(getString(Res.string.connection_error))
            HomeEffect.SomethingWentWrong -> snackBarHostState.showSnackbar(getString(Res.string.something_went_wrong))
        }
    }

    val firstLoginNotificationPermissionRequestedRepository =
        koinInject<FirstLoginNotificationPermissionRequestedRepository>()

    val permissionRequester = rememberPermissionRequestLauncher { permission, status ->
        val granted = when (status) {
            PermissionStatus.Granted -> true
            PermissionStatus.PermanentlyDeclined -> false
            PermissionStatus.TemporarilyDeclined -> false
        }

        onEvent(HomeEvent.OnPostNotificationPermission(granted))
    }


    LaunchedEffect(Unit) {
        if (firstLoginNotificationPermissionRequestedRepository.getAndSet()) {
            permissionRequester.launch(Permission.PostNotifications)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding))
        },
    )
}


