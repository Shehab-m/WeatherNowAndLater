package com.vodafone.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: String?
)