package com.vodafone.data.remote.model

import com.google.gson.annotations.SerializedName

data class DayTimeWeatherDto(
    @SerializedName("dt")
    val date: Int?,
    @SerializedName("dt_txt")
    val dateTxt: String?,
    @SerializedName("main")
    val main: MainDto?,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("weather")
    val weather: List<WeatherDto>?,
    @SerializedName("wind")
    val wind: WindDto?
)