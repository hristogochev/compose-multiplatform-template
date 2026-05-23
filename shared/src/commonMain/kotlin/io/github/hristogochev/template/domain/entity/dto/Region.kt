package io.github.hristogochev.template.domain.entity.dto

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class Region(
    val id: UInt,
    val name: String,
    val country: Country,
) : Parcelable, Comparable<Region> {
    companion object {
        val comparator: Comparator<Region> = compareBy<Region> { it.country }
            .thenBy { it.name }
            .thenBy { it.id }
    }

    override fun compareTo(other: Region): Int {
        return comparator.compare(this, other)
    }
}

fun List<Region>.sort(): List<Region> = sorted()
