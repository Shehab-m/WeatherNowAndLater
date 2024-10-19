package com.vodafone.weatherapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vodafone.weather.WeatherScreen

private val ROUTE = Screens.WeatherScreen.route

fun NavController.navigateToWeatherScreen() {
    navigate(ROUTE) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.weatherRoute(navigateToForecast: (String) -> Unit) {
    composable(ROUTE) {
        WeatherScreen(navigateToForecast = navigateToForecast)
    }
}