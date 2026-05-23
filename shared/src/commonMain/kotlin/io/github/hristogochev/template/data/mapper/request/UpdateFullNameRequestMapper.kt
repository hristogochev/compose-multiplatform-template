package io.github.hristogochev.template.data.mapper.request

import io.github.hristogochev.template.data.entity.request.UpdateFullNameRequestEntity
import io.github.hristogochev.template.domain.entity.request.UpdateFullNameRequest

fun UpdateFullNameRequest.toEntity(): UpdateFullNameRequestEntity {
    return UpdateFullNameRequestEntity(firstName = firstName, lastName = lastName)
}