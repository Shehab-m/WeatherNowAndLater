package com.vodafone.weatherapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnrememberedMutableState")
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val dynamicModuleDownloadUtil = remember(navController) { DynamicModuleDownloadUtil(context) }

    NavHost(
        navController = navController,
        startDestination = Screens.WeatherScreen.route
    ) {
        weatherRoute(dynamicModuleDownloadUtil::onDynamicClick)
    }
}