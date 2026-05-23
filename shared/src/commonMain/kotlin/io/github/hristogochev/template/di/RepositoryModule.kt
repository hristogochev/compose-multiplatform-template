package io.github.hristogochev.template.di

import app.cash.sqldelight.db.SqlDriver
import io.github.hristogochev.template.SQLDelightEntityDatabase
import io.github.hristogochev.template.data.adapter.table.CityLocalAdapter
import io.github.hristogochev.template.data.adapter.table.CountryLocalAdapter
import io.github.hristogochev.template.data.adapter.table.RegionLocalAdapter
import io.github.hristogochev.template.data.adapter.table.UserDetailsLocalAdapter
import io.github.hristogochev.template.data.repository.KtorRemoteRepository
import io.github.hristogochev.template.data.repository.local.SQLCityLocalRepository
import io.github.hristogochev.template.data.repository.local.SQLCountryLocalRepository
import io.github.hristogochev.template.data.repository.local.SQLRegionLocalRepository
import io.github.hristogochev.template.data.repository.local.SQLUserDetailsLocalRepository
import io.github.hristogochev.template.domain.repository.CurrentDateTimeRepository
import io.github.hristogochev.template.domain.repository.LocalRepository
import io.github.hristogochev.template.domain.repository.RemoteRepository
import io.github.hristogochev.template.domain.repository.local.CityLocalRepository
import io.github.hristogochev.template.domain.repository.local.CountryLocalRepository
import io.github.hristogochev.template.domain.repository.local.RegionLocalRepository
import io.github.hristogochev.template.domain.repository.local.UserDetailsLocalRepository

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    single<SQLDelightEntityDatabase> {
        val driver: SqlDriver = get()

        driver.execute(null, "PRAGMA foreign_keys = ON", 0)

        SQLDelightEntityDatabase(
            driver = driver,
            UserDetailsLocalAdapter = UserDetailsLocalAdapter,
            CountryLocalAdapter = CountryLocalAdapter,
            RegionLocalAdapter = RegionLocalAdapter,
            CityLocalAdapter = CityLocalAdapter,
        )
    }
    single<RemoteRepository> {
        KtorRemoteRepository(get())
    }


    single<UserDetailsLocalRepository> {
        SQLUserDetailsLocalRepository(get())
    }

    single<CountryLocalRepository> {
        SQLCountryLocalRepository(get())
    }
    single<RegionLocalRepository> {
        SQLRegionLocalRepository(get())
    }
    single<CityLocalRepository> {
        SQLCityLocalRepository(get())
    }

    singleOf(::CurrentDateTimeRepository)
    singleOf(::LocalRepository)
}