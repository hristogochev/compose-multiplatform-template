package io.github.hristogochev.template.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionEntity(
    @SerialName("api_version_code")
    val apiVersionCode: ULong,
    @SerialName("android_version_code")
    val androidVersionCode: ULong,
    @SerialName("ios_version_code")
    val iOSVersionCode: ULong,
    @SerialName("ios_app_id")
    val iOSAppId: String,
)