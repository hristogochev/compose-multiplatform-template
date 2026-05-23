package io.github.hristogochev.template.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.github.hristogochev.template.SQLDelightEntityDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun sqlDriverModule(): Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = SQLDelightEntityDatabase.Schema,
            context = get(),
            name = "template.db",
        )
    }
}