package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.Wind
import com.vodafone.data.remote.model.WindDto

fun WindDto.toEntity(): Wind {
    return Wind(
        deg = deg ?: 0,
        speed = speed ?: 0.0
    )
}
