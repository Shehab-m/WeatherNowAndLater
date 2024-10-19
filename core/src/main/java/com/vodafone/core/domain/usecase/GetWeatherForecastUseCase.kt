package com.vodafone.core.domain.usecase

import com.vodafone.core.domain.model.DayTimeWeather
import com.vodafone.core.repository.WeatherRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityName: String): List<List<DayTimeWeather>> {
        val forecast = repository.getWeatherForecast(cityName)
        return getSortedForecast(forecast.list)
    }

    private fun getSortedForecast(weatherData: List<DayTimeWeather>): List<List<DayTimeWeather>> {
        val now = LocalDateTime.now()

        return weatherData.groupBy { it.date.toLocalDate() }
            .map {
                it.value.sortedBy { dayWeather ->
                    Math.abs(dayWeather.date.until(now, java.time.temporal.ChronoUnit.MINUTES))
                }
            }.sortedBy { it.first().date.toLocalDate() }
    }

}