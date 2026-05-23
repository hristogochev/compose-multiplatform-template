package io.github.hristogochev.template.di


import io.github.hristogochev.template.AppViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

fun androidModule(): Module = module {
    viewModelOf(::AppViewModel)
}