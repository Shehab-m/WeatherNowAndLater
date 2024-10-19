package com.vodafone.forecast

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vodafone.core.R
import com.vodafone.core.designSystem.composable.WTopBar
import com.vodafone.core.designSystem.theme.WeatherAppTheme
import com.vodafone.core.utils.Constants.CITY_NAME_KEY
import com.vodafone.weatherutils.getWeatherIcon


class ForecastActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cityName = intent.getStringExtra(CITY_NAME_KEY) ?: ""
        val forecast =
            FakeForecastDataSource.getSortedForecast().map { it.map { it.toUIState(cityName) } }
        setContent {
            WeatherAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Column {
                        WTopBar(
                            title = "Forecast",
                            icon = painterResource(id = R.drawable.temperature_c),
                            backIcon = painterResource(id = R.drawable.arrow_left),
                            backIconColor = MaterialTheme.colorScheme.secondary,
                            onClickBack = { finish() },
                            topPadding = PaddingValues(innerPadding.calculateTopPadding())
                        )
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 20.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(forecast) { forecast ->
                                val dayTimes = forecast.toMutableList()
                                val firstDayTime = dayTimes.first()
                                dayTimes.removeAt(0)
                                Card(
                                    modifier = Modifier,
                                    elevation = CardDefaults.cardElevation(2.dp),
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        val weather = firstDayTime.weather
                                        CardCityWeather(
                                            icon = painterResource(id = getWeatherIcon(weather)),
                                            weather = weather,
                                            temperature = firstDayTime.temperature,
                                            windSpeed = firstDayTime.wind,
                                            cityName = firstDayTime.cityName,
                                        )
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp)
                                        ) {
                                            items(dayTimes) { dayTime ->
                                                val dayTimeWeather = dayTime.weather
                                                CardSmallCityWeather(
                                                    icon = painterResource(
                                                        id = getWeatherIcon(
                                                            dayTimeWeather
                                                        )
                                                    ),
                                                    weather = dayTimeWeather,
                                                    temperature = dayTime.temperature,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}