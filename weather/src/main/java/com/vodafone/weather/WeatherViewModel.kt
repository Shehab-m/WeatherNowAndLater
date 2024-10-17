package com.vodafone.weather

import com.vodafone.core.base.BaseViewModel
import com.vodafone.core.domain.usecase.GetCurrentWeatherUseCase
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : BaseViewModel<WeatherUiEffect>() {




}