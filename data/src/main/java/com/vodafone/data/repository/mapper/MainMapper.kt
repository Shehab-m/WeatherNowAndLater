package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.Main
import com.vodafone.data.remote.model.MainDto

fun MainDto.toEntity(): Main {
    return Main(
        feelsLike = feelsLike ?: 0.0,
        grndLevel = grndLevel ?: 0,
        humidity = humidity ?: 0,
        pressure = pressure ?: 0,
        seaLevel = seaLevel ?: 0,
        temp = temp ?: 0.0,
        tempKf = tempKf ?: 0.0,
        tempMax = tempMax ?: 0.0,
        tempMin = tempMin ?: 0.0
    )
}
