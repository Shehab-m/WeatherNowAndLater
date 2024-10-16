package com.vodafone.core.domain.usecase

import com.vodafone.core.domain.model.CityWeather
import com.vodafone.core.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityName: String): CityWeather {
        return repository.getCurrentWeather(cityName)
    }

}