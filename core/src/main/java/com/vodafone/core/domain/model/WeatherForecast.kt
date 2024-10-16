package com.vodafone.core.domain.model

data class WeatherForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DayTimeWeather>,
    val message: Int
)
