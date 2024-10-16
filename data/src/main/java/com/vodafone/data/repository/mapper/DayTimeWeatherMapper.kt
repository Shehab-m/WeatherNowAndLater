package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.DayTimeWeather
import com.vodafone.core.domain.model.Main
import com.vodafone.core.domain.model.Wind
import com.vodafone.data.remote.model.DayTimeWeatherDto
import com.vodafone.data.utils.toLocalDateTime
import java.time.LocalDateTime

fun DayTimeWeatherDto.toEntity(): DayTimeWeather {
    return DayTimeWeather(
        date = date?.toLocalDateTime() ?: LocalDateTime.MIN,
        dateTxt = dateTxt ?: "",
        main = main?.toEntity() ?: Main(0.0, 0, 0, 0, 0, 0.0, 0.0, 0.0, 0.0),
        visibility = visibility ?: 0,
        weather = weather?.map { it.toEntity() } ?: emptyList(),
        wind = wind?.toEntity() ?: Wind(0, 0.0)
    )
}
