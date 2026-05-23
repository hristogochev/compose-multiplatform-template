package io.github.hristogochev.template.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.github.hristogochev.template.data.repository.SettingsFirstRepository
import io.github.hristogochev.template.data.repository.SettingsSkippedVersionRepository
import io.github.hristogochev.template.data.repository.SettingsUserRepository
import io.github.hristogochev.template.domain.repository.FirstLoginNotificationPermissionRequestedRepository
import io.github.hristogochev.template.domain.repository.SkippedVersionRepository
import io.github.hristogochev.template.domain.repository.UserRepository
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSUserDefaults
import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleAfterFirstUnlock
import platform.Security.kSecAttrService

@OptIn(
    ExperimentalForeignApi::class, ExperimentalSettingsApi::class,
    ExperimentalSettingsImplementation::class
)
actual fun userModule(): Module = module {
    single<UserRepository> {
        val serviceName = "template_users"
        val settings = KeychainSettings(
            kSecAttrService to CFBridgingRetain(serviceName),
            kSecAttrAccessible to kSecAttrAccessibleAfterFirstUnlock
        )

        SettingsUserRepository(settings)
    }

    single<SkippedVersionRepository> {
        val delegate = NSUserDefaults(suiteName = "template_skipped_version")
        val settings: Settings = NSUserDefaultsSettings(delegate)

        SettingsSkippedVersionRepository(settings)
    }

    single<FirstLoginNotificationPermissionRequestedRepository> {
        val delegate = NSUserDefaults(suiteName = "template_first_notifications")
        val settings: Settings = NSUserDefaultsSettings(delegate)

        SettingsFirstRepository(settings)
    }
}