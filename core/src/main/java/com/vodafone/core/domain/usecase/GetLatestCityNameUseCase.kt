package com.vodafone.core.domain.usecase

import com.vodafone.core.repository.WeatherRepository
import javax.inject.Inject

class GetLatestCityNameUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(): String? {
        return repository.getCityName()
    }

}