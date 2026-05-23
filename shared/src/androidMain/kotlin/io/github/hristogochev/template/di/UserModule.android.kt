package io.github.hristogochev.template.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.github.hristogochev.template.data.repository.SettingsFirstRepository
import io.github.hristogochev.template.data.repository.SettingsSkippedVersionRepository
import io.github.hristogochev.template.data.repository.SettingsUserRepository
import io.github.hristogochev.template.domain.repository.FirstLoginNotificationPermissionRequestedRepository
import io.github.hristogochev.template.domain.repository.SkippedVersionRepository
import io.github.hristogochev.template.domain.repository.UserRepository
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun userModule(): Module = module {
    single<UserRepository> {
        val context: Context = get()
        val masterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "template_users",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val settings: Settings = SharedPreferencesSettings(sharedPreferences)

        SettingsUserRepository(settings)
    }
    single<SkippedVersionRepository> {
        val context: Context = get()

        val sharedPreferences =
            context.getSharedPreferences("template_skipped_version", Context.MODE_PRIVATE)
        val settings: Settings = SharedPreferencesSettings(sharedPreferences)

        SettingsSkippedVersionRepository(settings)
    }
    single<FirstLoginNotificationPermissionRequestedRepository> {
        val context: Context = get()

        val sharedPreferences =
            context.getSharedPreferences("template_first_notifications", Context.MODE_PRIVATE)
        val settings: Settings = SharedPreferencesSettings(sharedPreferences)

        SettingsFirstRepository(settings)
    }
}