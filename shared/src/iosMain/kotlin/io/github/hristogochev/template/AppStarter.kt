package io.github.hristogochev.template

import io.github.hristogochev.template.domain.repository.UserRepository
import io.github.hristogochev.template.presentation.app.AppPrepared
import io.github.hristogochev.template.presentation.app.prepareBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "AppStarter")
class AppStarter : KoinComponent {
    private val userRepository: UserRepository by inject()

    @ObjCName("prepare")
    fun prepare(): AppPrepared = prepareBlocking(userRepository, null)
}