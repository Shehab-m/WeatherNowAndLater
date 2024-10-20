package com.vodafone.core.domain.usecase

import com.vodafone.core.repository.WeatherRepository
import javax.inject.Inject

class SaveCityNameUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityName: String) {
        return repository.saveCityName(cityName)
    }

}