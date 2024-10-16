package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.City
import com.vodafone.core.domain.model.Coord
import com.vodafone.data.remote.model.CityDto

fun CityDto.toEntity(): City {
    return City(
        coord = coord?.toEntity() ?: Coord(0.0, 0.0),
        country = country ?: "",
        id = id ?: 0,
        name = name ?: "",
        population = population ?: 0,
        sunrise = sunrise ?: 0,
        sunset = sunset ?: 0,
        timezone = timezone ?: 0
    )
}

