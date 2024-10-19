package com.vodafone.forecast

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.vodafone.core.designSystem.theme.WeatherAppTheme
import com.vodafone.weatherutils.getWeatherIcon

@Composable
@Preview
fun AuthScreenPreview() {
    WeatherAppTheme {
        CardSmallCityWeather(
            icon = painterResource(id = getWeatherIcon("Clear")),
            weather = "Clear",
            temperature = 345.0,
        )
    }
}
