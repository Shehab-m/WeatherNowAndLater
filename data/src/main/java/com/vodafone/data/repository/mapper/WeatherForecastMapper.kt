package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.City
import com.vodafone.core.domain.model.Coord
import com.vodafone.core.domain.model.WeatherForecast
import com.vodafone.data.remote.model.WeatherForecastDto

fun WeatherForecastDto.toEntity(): WeatherForecast {
    return WeatherForecast(
        city = city?.toEntity() ?: City(
            Coord(0.0, 0.0),
            "",
            0,
            "",
            0,
            0,
            0,
            0
        ),
        cnt = cnt ?: 0,
        cod = cod ?: "",
        list = list?.map { it.toEntity() } ?: emptyList(),
        message = message ?: 0
    )
}
