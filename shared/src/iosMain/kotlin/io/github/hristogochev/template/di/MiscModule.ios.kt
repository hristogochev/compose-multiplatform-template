package io.github.hristogochev.template.di

import io.github.hristogochev.template.util.IOSPhoneCaller
import io.github.hristogochev.template.util.IOSStoreOpener
import io.github.hristogochev.template.util.IOSVersionCodeGetter
import io.github.hristogochev.template.util.PhoneCaller
import io.github.hristogochev.template.util.StoreOpener
import io.github.hristogochev.template.util.VersionCodeGetter
import org.koin.dsl.module

actual fun miscModule() = module {
    single<PhoneCaller> {
        IOSPhoneCaller()
    }
    single<StoreOpener> {
        IOSStoreOpener()
    }
    single<VersionCodeGetter> {
        IOSVersionCodeGetter()
    }
}