package com.vodafone.forecast

import com.vodafone.core.base.BaseUiEffect

sealed interface ForecastUiEffect : BaseUiEffect {
    data object ShowErrorToast : ForecastUiEffect
}