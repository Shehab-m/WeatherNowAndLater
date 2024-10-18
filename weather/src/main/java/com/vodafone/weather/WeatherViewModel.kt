package com.vodafone.weather

import android.content.res.Resources.NotFoundException
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.vodafone.core.base.BaseViewModel
import com.vodafone.core.domain.model.CityWeather
import com.vodafone.core.domain.usecase.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : BaseViewModel<WeatherUiEffect>() {

    var cityWeather by mutableStateOf(CityWeatherUIState())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isError by mutableStateOf(false)
        private set

    var isCityNameCorrect by mutableStateOf(true)
        private set

    var searchQuery by mutableStateOf("")
        private set

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            snapshotFlow { searchQuery }.debounce(300).distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        searchCities(query)
                    }
                }
        }
    }


    private fun searchCities(query: String) {
        searchQuery = query
        isLoading = true
        isError = false
        tryToExecute(
            { getCurrentWeatherUseCase(query) }, ::onSuccessSearch, ::onError
        )
    }

    private fun onSuccessSearch(weather: CityWeather) {
        isLoading = false
        isCityNameCorrect = true
        cityWeather = weather.toUIState()
    }

    private fun onError(error: Exception) {
        isLoading = false
        if (error is NotFoundException) {
            isCityNameCorrect = false
        } else {
            isError = true
            sendEffect(WeatherUiEffect.ShowErrorToast)
            Log.e("TAG", "onError: ${error.message} ")
        }
    }

    fun onClickTryAgain() {
        searchCities(searchQuery)
    }

    fun onChangeSearchQuery(query: String) {
        searchQuery = query
    }
}

