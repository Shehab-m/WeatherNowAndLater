package com.vodafone.forecast

import android.util.Log
import com.vodafone.core.base.BaseViewModel
import com.vodafone.core.domain.model.DayTimeWeather
import com.vodafone.core.domain.usecase.GetWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) : BaseViewModel<ForecastUiEffect>(), ForecastInteractionListener {

    private val _state = MutableStateFlow(ForecastUiState())
    val state = _state.asStateFlow()

    private fun getForecast(cityName: String) {
        _state.update { it.copy(isError = false, isLoading = true) }
        tryToExecute(
            { getWeatherForecastUseCase(cityName) }, ::onSuccessGetRepos, ::onError
        )
    }

    private fun onSuccessGetRepos(forecast: List<List<DayTimeWeather>>) {
        _state.update {
            it.copy(
                isLoading = false,
                forecast = forecast.map { it.map { it.toUIState(state.value.cityName) } }
            )
        }
    }

    private fun onError(error: Exception) {
        Log.e("onError: ", "${error.message}")
        _state.update { it.copy(isError = true, isLoading = false) }
        sendEffect(ForecastUiEffect.ShowErrorToast)
    }

    override fun onClickTryAgain() {
        getForecast(_state.value.cityName)
    }

    override fun initCityName(cityName: String) {
        _state.update { it.copy(cityName = cityName) }
        getForecast(cityName)
    }

}

