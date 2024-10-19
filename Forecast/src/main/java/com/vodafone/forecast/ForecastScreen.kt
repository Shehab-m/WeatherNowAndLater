package com.vodafone.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.R
import com.vodafone.core.designSystem.composable.Loading
import com.vodafone.core.designSystem.composable.WAnimationContent
import com.vodafone.core.designSystem.composable.WTopBar
import com.vodafone.core.designSystem.theme.WeatherAppTheme
import com.vodafone.weatherutils.getWeatherIcon
import com.vodafone.weatherutils.toFormatedTemperatureText
import com.vodafone.weatherutils.toFormatedWindSpeedText

@Composable
fun ForecastScreen(
    cityName: String,
    onClickBack: () -> Unit,
    viewModel: ForecastViewModel = hiltViewModel()
) {


    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.initCityName(cityName)
    }
    ForecastContent(
        state = state,
        listener = viewModel,
        onClickBack = onClickBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastContent(
    state: ForecastUiState,
    listener: ForecastInteractionListener,
    onClickBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            WTopBar(
                title = "Forecast",
                icon = painterResource(id = R.drawable.temperature_c),
                backIcon = painterResource(id = R.drawable.arrow_left),
                backIconColor = MaterialTheme.colorScheme.secondary,
                onClickBack = onClickBack
            )
        }
    ) { innerPadding ->
        WAnimationContent(
            state = state.isLoading,
            content = {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = innerPadding.calculateTopPadding())
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(state.forecast) { forecast ->
                        val dayTimes = forecast.toMutableList()
                        val firstDayTime = dayTimes.first()
                        dayTimes.removeAt(0)
                        Box(modifier = Modifier.shadow(2.dp)) {
                            Column(
                                modifier = Modifier.padding(8.dp),
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
                                ) {
                                    items(dayTimes) { dayTime ->
                                        val dayTimeWeather = dayTime.weather
                                        CardSmallCityWeather(
                                            icon = painterResource(id = getWeatherIcon(dayTimeWeather)),
                                            weather = "dayTimeWeather",
                                            temperature = dayTime.temperature,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            loadingContent = {
                Loading(state = state.isLoading)
            },
            isError = state.isError,
            onClickTryAgain = listener::onClickTryAgain
        )
    }
}

@Composable
fun CardSmallCityWeather(
    modifier: Modifier = Modifier,
    icon: Painter,
    weather: String,
    temperature: Double,
    shape: RoundedCornerShape = RoundedCornerShape(24.dp)
) {
    Card(
        modifier = modifier
            .width(80.dp)
            .height(120.dp),
        shape = shape,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = temperature.toFormatedTemperatureText(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
            Icon(
                modifier = Modifier.size(30.dp),
                painter = icon,
                contentDescription = "Category icon",
                tint = Color.Unspecified
            )
            Text(
                text = weather,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun CardCityWeather(
    modifier: Modifier = Modifier,
    icon: Painter,
    weather: String,
    temperature: Double,
    windSpeed: Double,
    cityName: String,
    shape: RoundedCornerShape = RoundedCornerShape(24.dp)
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = temperature.toFormatedTemperatureText(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(top = 30.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Wind ${windSpeed.toFormatedWindSpeedText()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(top = 24.dp),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cityName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = weather,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(end = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(160.dp),
                painter = icon,
                contentDescription = "Category icon",
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
@Preview
fun ForecastScreenPreview() {
    WeatherAppTheme {
        ForecastContent(
            state = ForecastUiState(),
            onClickBack = {},
            listener = object : ForecastInteractionListener {
                override fun onClickTryAgain() {}
                override fun initCityName(cityName: String) {}
            }
        )
    }
}