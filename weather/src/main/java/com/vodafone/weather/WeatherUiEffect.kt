package com.vodafone.weather

import com.vodafone.core.base.BaseUiEffect

sealed interface WeatherUiEffect : BaseUiEffect {
    data object ShowErrorToast : WeatherUiEffect
}