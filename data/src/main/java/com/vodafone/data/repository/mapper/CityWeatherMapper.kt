package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.CityWeather
import com.vodafone.core.domain.model.Coord
import com.vodafone.core.domain.model.Main
import com.vodafone.core.domain.model.Sys
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.Wind
import com.vodafone.data.remote.model.CityWeatherDto
import com.vodafone.data.utils.toLocalDateTime
import java.time.LocalDateTime

fun CityWeatherDto.toEntity(): CityWeather {
    return CityWeather(base = base ?: "",
        cod = cod ?: 0,
        coord = coord?.toEntity() ?: Coord(0.0, 0.0),
        date = date?.toLocalDateTime() ?: LocalDateTime.MIN,
        id = id ?: 0,
        main = main?.toEntity() ?: Main(0.0, 0, 0, 0, 0, 0.0, 0.0, 0.0, 0.0),
        name = name ?: "",
        sys = sys?.toEntity() ?: Sys("", 0, LocalDateTime.MIN, LocalDateTime.MIN, 0),
        timezone = timezone ?: 0,
        visibility = visibility ?: 0,
        weather = weather?.map { it?.toEntity() ?: Weather("", "", 0, "") } ?: emptyList(),
        wind = wind?.toEntity() ?: Wind(0, 0.0))
}
