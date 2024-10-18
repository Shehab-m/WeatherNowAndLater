package com.vodafone.data.repository

import com.vodafone.core.domain.model.CityWeather
import com.vodafone.core.domain.model.WeatherForecast
import com.vodafone.core.repository.WeatherRepository
import com.vodafone.data.local.DataStoreManager
import com.vodafone.data.remote.WeatherApiService
import com.vodafone.data.repository.mapper.toEntity
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: WeatherApiService,
    private val dataStore: DataStoreManager
) : WeatherRepository {

    override suspend fun getCurrentWeather(cityName: String): CityWeather {
        return wrapResponse { service.getCurrentWeather(cityName) }.toEntity()
    }

    override suspend fun getWeatherForecast(cityName: String): WeatherForecast {
        return wrapResponse { service.getWeatherForecast(cityName) }.toEntity()
    }

    override suspend fun getCityName(): String? {
        return dataStore.getCityName()
    }

    override suspend fun saveCityName(cityName: String) {
        dataStore.saveCityName(cityName)
    }

    private suspend fun <T> wrapResponse(
        function: suspend () -> Response<T>
    ): T {
        return try {
            val apiResponse = function()
            if (apiResponse.isSuccessful) {
                val responseBody = apiResponse.body()
                Timber.tag("Tag").d("response Success: %s", responseBody)
                responseBody ?: throw Exception("Data not found!")
            } else {
                val message = apiResponse.message()
                Timber.tag("Tag").d("response Not Success:: %s", message)
                throw Exception(message)
            }
        } catch (e: Exception) {
            Timber.tag("Tag").e("response Error:%s", e.message)
            throw Exception("${e.message}")
        }
    }
}