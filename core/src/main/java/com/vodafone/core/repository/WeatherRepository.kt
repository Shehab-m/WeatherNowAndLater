package com.vodafone.core.repository

import com.vodafone.core.domain.model.CityWeather
import com.vodafone.core.domain.model.WeatherForecast

interface WeatherRepository {

    suspend fun getCurrentWeather(cityName: String): CityWeather

    suspend fun getWeatherForecast(cityName: String): WeatherForecast

    suspend fun getCityName(): String?

    suspend fun saveCityName(cityName: String)
}