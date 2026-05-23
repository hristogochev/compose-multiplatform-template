package io.github.hristogochev.template.domain.repository


import io.github.hristogochev.template.data.mapper.local.toLocal
import io.github.hristogochev.template.domain.entity.auth.UserDetails
import io.github.hristogochev.template.domain.entity.dto.City
import io.github.hristogochev.template.domain.entity.dto.Country
import io.github.hristogochev.template.domain.entity.dto.Region
import io.github.hristogochev.template.domain.repository.local.CityLocalRepository
import io.github.hristogochev.template.domain.repository.local.CountryLocalRepository
import io.github.hristogochev.template.domain.repository.local.RegionLocalRepository
import io.github.hristogochev.template.domain.repository.local.UserDetailsLocalRepository

class LocalRepository(
    val userDetails: UserDetailsLocalRepository,
    val country: CountryLocalRepository,
    val region: RegionLocalRepository,
    val city: CityLocalRepository,
) {

    suspend fun upsertUserDetails(
        previousUserDetailsIds: List<ULong>,
        newUserDetails: List<UserDetails>,
    ) {
        userDetails.upsertAll(
            previousUserDetailsIds,
            newUserDetails.map { it.toLocal() }
        )
    }

    suspend fun upsertCities(
        previousCityIds: List<ULong>,
        newCities: List<City>,
        upsertRegions: Boolean = true,
    ) {
        if (upsertRegions) {
            val newRegions = newCities
                .map { it.region }

            upsertRegions(emptyList(), newRegions)
        }

        city.upsertAll(previousCityIds, newCities.map { it.toLocal() })
    }

    suspend fun upsertRegions(
        previousRegionIds: List<UInt>,
        newRegions: List<Region>,
        upsertCountries: Boolean = true,
    ) {
        if (upsertCountries) {
            val newCountries = newRegions
                .map { it.country }

            upsertCountries(emptyList(), newCountries)
        }
        region.upsertAll(previousRegionIds, newRegions.map { it.toLocal() })
    }

    suspend fun upsertCountries(
        previousCountryIds: List<UInt>,
        newCountries: List<Country>,
    ) {
        country.upsertAll(previousCountryIds, newCountries.map { it.toLocal() })
    }
}