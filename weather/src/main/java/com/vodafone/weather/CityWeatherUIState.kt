package com.vodafone.weather

import com.vodafone.core.domain.model.CityWeather

data class CityWeatherUIState(
    val temperature: Double = 0.0,
    val cityName: String = "",
    val wind: Double = 0.0,
    val weather: String = "",
)

fun CityWeather.toUIState(): CityWeatherUIState {
    return CityWeatherUIState(
        temperature = main.temp,
        cityName = name,
        wind = wind.speed,
        weather = weather.first().main
    )
}
