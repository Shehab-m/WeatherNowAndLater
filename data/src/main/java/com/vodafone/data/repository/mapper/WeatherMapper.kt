package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.Weather
import com.vodafone.data.remote.model.WeatherDto

fun WeatherDto.toEntity(): Weather {
    return Weather(
        description = description ?: "",
        icon = icon ?: "",
        id = id ?: 0,
        main = main ?: ""
    )
}
