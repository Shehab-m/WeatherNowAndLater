package com.vodafone.core.domain.usecase

import com.vodafone.core.domain.model.WeatherForecast
import com.vodafone.core.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityName: String): WeatherForecast {
        return repository.getWeatherForecast(cityName)
    }

}