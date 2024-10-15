package com.vodafone.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("city")
    val city: CityDto,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<DayTimeWeatherDto>,
    @SerializedName("message")
    val message: Int
)