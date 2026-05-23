package io.github.hristogochev.template.data.mapper.response

import io.github.hristogochev.template.data.entity.response.VersionEntity
import io.github.hristogochev.template.domain.entity.dto.Version

fun VersionEntity.toVersion(): Version {
    return Version(apiVersionCode, androidVersionCode, iOSAppId)
}