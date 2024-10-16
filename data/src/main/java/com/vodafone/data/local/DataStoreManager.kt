package com.vodafone.data.local

interface DataStoreManager {
    suspend fun getCityName(): String?
    suspend fun saveCityName(cityName: String)
}