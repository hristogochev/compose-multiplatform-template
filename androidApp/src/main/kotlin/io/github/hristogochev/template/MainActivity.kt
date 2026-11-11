package io.github.hristogochev.template

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import io.github.hristogochev.template.presentation.app.App
import io.github.hristogochev.template.presentation.app.AppPrepared
import io.github.hristogochev.template.util.notification.getNotificationClick
import io.github.hristogochev.template.util.notification.notificationClickHandler
import io.github.hristogochev.template.util.notification.toOptional
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val appViewModel by viewModel<AppViewModel> {
        parametersOf(getNotificationClick().toOptional().also {
            println("Initial notification redirect: $it")
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (savedInstanceState != null && !recreatedForConfigurationChange) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
            return
        }

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                appViewModel.state.value is AppPrepared.Preparing
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )

        window?.apply {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }

        notificationClickHandler()

        setContent {
            val prepared by appViewModel.state.collectAsState()

            App(prepared = prepared)
        }
    }

    override fun onDestroy() {
        recreatedForConfigurationChange = isChangingConfigurations
        super.onDestroy()
    }

    companion object {
        private var recreatedForConfigurationChange = false
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(prepared = AppPrepared.Preparing)
}
