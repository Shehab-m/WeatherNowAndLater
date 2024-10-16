package com.vodafone.data.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Int.toLocalDateTime(): LocalDateTime? {
    return try {
        val instant = Instant.ofEpochSecond(this.toLong())
        LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    } catch (e: Exception) {
        println("Error converting timestamp: ${e.message}")
        null
    }
}