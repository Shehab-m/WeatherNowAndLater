package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.Sys
import com.vodafone.data.remote.model.SysDto
import com.vodafone.data.utils.toLocalDateTime
import java.time.LocalDateTime

fun SysDto.toEntity(): Sys {
    return Sys(
        country = country ?: "",
        id = id ?: 0,
        sunrise = sunrise?.toLocalDateTime() ?: LocalDateTime.MIN,
        sunset = sunset?.toLocalDateTime() ?: LocalDateTime.MIN,
        type = type ?: 0
    )
}
