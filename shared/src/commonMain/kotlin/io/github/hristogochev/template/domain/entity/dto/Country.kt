package io.github.hristogochev.template.domain.entity.dto

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class Country(
    val id: UInt,
    val name: String,
) : Parcelable, Comparable<Country> {
    companion object {
        val comparator: Comparator<Country> = compareBy<Country> { it.name }
            .thenBy { it.id }
    }

    override fun compareTo(other: Country): Int {
        return comparator.compare(this, other)
    }
}
