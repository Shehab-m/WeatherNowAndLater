package com.vodafone.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val actions = remember(navController) { Actions(context) }
    NavHost(
        navController = navController,
        startDestination = Screens.WeatherScreen.route
    ) {
        weatherRoute(actions::onDynamicClick)
    }
}

