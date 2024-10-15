package com.vodafone.core.domain.model

import java.time.LocalDateTime

data class DayTimeWeather(
    val date: LocalDateTime,
    val dateTxt: String,
    val main: Main,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)
