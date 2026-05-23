package io.github.hristogochev.template.presentation.app

import io.github.hristogochev.template.domain.entity.auth.UserCredentials
import io.github.hristogochev.template.domain.repository.UserRepository
import io.github.hristogochev.template.presentation.home.HomeState
import io.github.hristogochev.template.presentation.auth.landing.LandingState
import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
sealed interface AppPrepared : Parcelable {
    data object Preparing : AppPrepared

    @Parcelize
    sealed interface Prepared : AppPrepared, Parcelable {

        data class Landing(val landingState: LandingState) : Prepared

        data class Home(val homeState: HomeState, val payloadData: Map<String, String>?) : Prepared
    }
}


suspend fun prepare(userRepository: UserRepository, notificationData: Map<String, String>?) =
    mapPrepared(userRepository.get(), notificationData)

fun prepareBlocking(userRepository: UserRepository, notificationData: Map<String, String>?) =
    mapPrepared(userRepository.getBlocking(), notificationData)

private fun mapPrepared(
    user: UserCredentials?,
    notificationData: Map<String, String>?,
): AppPrepared.Prepared {

    return if (user == null) {
        AppPrepared.Prepared.Landing(landingState = LandingState)
    } else {
        AppPrepared.Prepared.Home(HomeState(user), notificationData)
    }
}