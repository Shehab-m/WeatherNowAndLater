package com.vodafone.core.domain.model

import java.time.LocalDateTime

data class CityWeather(
    val base: String,
    val cod: Int,
    val coord: Coord,
    val date: LocalDateTime,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
