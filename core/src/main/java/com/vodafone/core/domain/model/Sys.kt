package com.vodafone.core.domain.model

import java.time.LocalDateTime

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val type: Int
)