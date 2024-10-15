package com.vodafone.data.remote

import com.vodafone.data.remote.model.CityWeatherDto
import com.vodafone.data.remote.model.WeatherForecastDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
    ): Response<CityWeatherDto>

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") cityName: String,
    ): Response<WeatherForecastDto>

}