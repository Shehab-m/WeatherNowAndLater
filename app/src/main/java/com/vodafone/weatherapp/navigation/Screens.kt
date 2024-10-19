package com.vodafone.weatherapp.navigation

sealed class Screens(val route: String) {
    data object WeatherScreen : Screens("weatherScreen")
}