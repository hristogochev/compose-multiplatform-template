package io.github.hristogochev.template.di

import io.github.hristogochev.template.util.AndroidVersionCodeGetter
import io.github.hristogochev.template.util.VersionCodeGetter
import io.github.hristogochev.template.util.AndroidStoreOpener
import io.github.hristogochev.template.util.StoreOpener
import io.github.hristogochev.template.util.AndroidPhoneCaller
import io.github.hristogochev.template.util.PhoneCaller
import org.koin.dsl.module

actual fun miscModule() = module {
    single<PhoneCaller> {
        AndroidPhoneCaller(get())
    }
    single<StoreOpener> {
        AndroidStoreOpener(get())
    }
    single<VersionCodeGetter> {
        AndroidVersionCodeGetter(get())
    }
}