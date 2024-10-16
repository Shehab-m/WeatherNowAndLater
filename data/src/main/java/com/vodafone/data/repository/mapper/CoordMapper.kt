package com.vodafone.data.repository.mapper

import com.vodafone.core.domain.model.Coord
import com.vodafone.data.remote.model.CoordDto

fun CoordDto.toEntity(): Coord {
    return Coord(
        lat = lat ?: 0.0,
        lon = lon ?: 0.0
    )
}