package com.vodafone.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImp @Inject constructor(context: Context) : DataStoreManager {

    companion object {
        private const val PREFERENCES_FILE_NAME = "weather_file"
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("city_name")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        PREFERENCES_FILE_NAME
    )

    private val dataStore = context.dataStore


    override suspend fun getCityName(): String? {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }.first()
    }

    override suspend fun saveCityName(cityName: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = cityName
        }
    }

}