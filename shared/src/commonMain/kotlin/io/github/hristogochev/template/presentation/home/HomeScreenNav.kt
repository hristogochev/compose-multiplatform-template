package io.github.hristogochev.template.presentation.home

import androidx.compose.runtime.Composable
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize
import io.github.hristogochev.vortex.screen.Screen

@Parcelize
data class HomeScreenNav(
    private val homeInitialState: HomeState,
) : Screen, Parcelable {


    @Composable
    override fun Content() {



    }
}
