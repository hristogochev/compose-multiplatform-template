package io.github.hristogochev.template.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.github.hristogochev.template.SQLDelightEntityDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun sqlDriverModule(): Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(SQLDelightEntityDatabase.Schema, "template_local_db.db")
    }
}
