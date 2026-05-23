package io.github.hristogochev.template.di

import io.github.hristogochev.template.presentation.home.HomeMessageCommunicator
import io.github.hristogochev.template.presentation.home.HomeNavEffectCommunicator
import io.github.hristogochev.template.presentation.main.MainMessageCommunicator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val communicatorsModule = module {
    singleOf(::MainMessageCommunicator)
    singleOf(::HomeMessageCommunicator)
    singleOf(::HomeNavEffectCommunicator)
}