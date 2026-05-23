import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerilization)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.sqldelight)
}

kotlin {

    cocoapods {
        ios.deploymentTarget = "14.1"

        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"

        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "Shared"
            isStatic = true
            binaryOption("bundleId", "io.github.hristogochev.template")
        }

        pod("FirebaseMessaging", version = "11.5.0")
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            binaryOption("bundleId", "io.github.hristogochev.template")
        }
    }


    androidLibrary {
       namespace = "io.github.hristogochev.template.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           val parcelableElements = listOf(
               "-P",
               "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=io.github.hristogochev.template.util.Parcelize"
           )
           freeCompilerArgs.addAll(parcelableElements)

           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
    }
    
    sourceSets {
        androidMain.dependencies {
            // Compose
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.activity)

            // Koin
            implementation(libs.koin.android)
            // Ktor
            implementation(libs.ktor.client.android)
            // Encrypted shared preferences
            implementation(libs.settings.android.encrypted)
            // SQLDelight
            implementation(libs.sqldelight.driver.android)
            // Lifecycle
            implementation(libs.lifecycle.runtime)
            implementation(libs.lifecycle.process)
            // Firebase
            implementation(libs.firebaseCloudMessaging)
        }
        commonMain.dependencies {
            // Compose
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.adaptive)

            // Vortex navigation
            implementation(libs.vortex)
            implementation(libs.vortex.koin)

            // Koin dependency injection
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            // Serialization
            implementation(libs.kotlinx.serliaizationJson)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.serilization)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serilization)
            implementation(libs.ktor.serilizationKotlinxJson)

            // Settings
            implementation(libs.settings.multiplatform)
            implementation(libs.settings.multiplatformSerialization)

            // Datetime
            implementation(libs.kotlinx.datetime)

            // IO
            implementation(libs.kotlinx.io)

            // SQLDelight
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.sqldelight.primitive.adapters)

            // Coil
            implementation(libs.landscapistCoil)

            // Perk
            implementation(libs.perk)
        }
        iosMain.dependencies {
            // Ktor
            implementation(libs.ktor.client.darwin)

            // SQLDelight
            implementation(libs.sqldelight.driver.native)
            implementation(libs.stately.common)
            implementation(libs.stately.concurrency)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}


sqldelight {
    databases {
        create("SQLDelightEntityDatabase") {
            packageName.set("io.github.hristogochev.template")
        }
    }
}