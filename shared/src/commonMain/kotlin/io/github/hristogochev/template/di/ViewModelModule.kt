package io.github.hristogochev.template.di

import io.github.hristogochev.template.presentation.auth.landing.LandingViewModel
import io.github.hristogochev.template.presentation.auth.login_first.LoginFirstViewModel
import io.github.hristogochev.template.presentation.auth.login_second.LoginSecondViewModel
import io.github.hristogochev.template.presentation.auth.register_first.RegisterFirstViewModel
import io.github.hristogochev.template.presentation.auth.register_second.RegisterSecondViewModel
import io.github.hristogochev.template.presentation.auth.register_success.RegisterSuccessViewModel
import io.github.hristogochev.template.presentation.home.HomeViewModel
import io.github.hristogochev.template.presentation.main.MainViewModel
import io.github.hristogochev.template.presentation.update.NeedsUpdateViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun viewModelModule() = module {
    factoryOf(::MainViewModel)
    factoryOf(::NeedsUpdateViewModel)
    factoryOf(::LandingViewModel)
    factoryOf(::RegisterFirstViewModel)
    factoryOf(::RegisterSecondViewModel)
    factoryOf(::RegisterSuccessViewModel)
    factoryOf(::LoginFirstViewModel)
    factoryOf(::LoginSecondViewModel)
    factoryOf(::HomeViewModel)
}