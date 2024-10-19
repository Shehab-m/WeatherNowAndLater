package com.vodafone.forecast

interface ForecastInteractionListener {
    fun onClickTryAgain()
    fun initCityName(cityName: String)
}