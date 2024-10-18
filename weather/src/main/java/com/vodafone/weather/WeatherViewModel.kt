package com.vodafone.weather

import com.vodafone.core.base.BaseViewModel
import com.vodafone.core.domain.usecase.GetCurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : BaseViewModel<WeatherUiEffect>() {




}