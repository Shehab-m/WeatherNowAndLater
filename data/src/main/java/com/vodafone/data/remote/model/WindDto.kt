package com.vodafone.data.remote.model

import com.google.gson.annotations.SerializedName

data class WindDto(
    @SerializedName("deg")
    val deg: Int?,
    @SerializedName("gust")
    val gust: Double?,
    @SerializedName("speed")
    val speed: Double?
)