package com.vodafone.forecast

import com.vodafone.core.domain.model.DayTimeWeather

data class ForecastUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val cityName: String = "",
    val forecast: List<List<CityWeatherUIState>> = emptyList()
)

data class CityWeatherUIState(
    val temperature: Double = 0.0,
    val cityName: String = "",
    val wind: Double = 0.0,
    val weather: String = "",
)

fun DayTimeWeather.toUIState(cityName: String): CityWeatherUIState {
    return CityWeatherUIState(
        temperature = main.temp,
        cityName = cityName,
        wind = wind.speed,
        weather = weather.first().main
    )
}