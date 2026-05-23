package io.github.hristogochev.template.presentation.update

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class NeedsUpdateState(
    val apiOrApp: NeedsUpdateApiOrApp,
    val iOSAppId: String
) : Parcelable

@Parcelize
sealed interface NeedsUpdateApiOrApp : Parcelable {
    data class Api(val localVersionCode: ULong, val remoteVersionCode: ULong) : NeedsUpdateApiOrApp
    data class App(
        val localVersionCode: ULong,
        val remoteVersionCode: ULong,
    ) : NeedsUpdateApiOrApp
}
