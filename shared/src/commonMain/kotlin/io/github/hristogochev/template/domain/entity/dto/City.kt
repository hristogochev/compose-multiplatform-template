package io.github.hristogochev.template.domain.entity.dto

import io.github.hristogochev.template.util.Parcelable
import io.github.hristogochev.template.util.Parcelize

@Parcelize
data class City(
    val id: ULong,
    val name: String,
    val region: Region,
) : Parcelable, Comparable<City> {
    companion object {
        val comparator: Comparator<City> = compareBy<City> { it.region }
            .thenBy { it.name }
            .thenBy { it.id }
    }

    override fun compareTo(other: City): Int {
        return comparator.compare(this, other)
    }
}

fun List<City>.sort(): List<City> = sorted()