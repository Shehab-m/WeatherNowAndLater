package com.vodafone.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.R
import com.vodafone.core.designSystem.composable.Loading
import com.vodafone.core.designSystem.composable.WAnimationContent
import com.vodafone.core.designSystem.composable.WAnimationContentState
import com.vodafone.core.designSystem.composable.WFilledButton
import com.vodafone.core.designSystem.composable.WSearchField
import com.vodafone.core.designSystem.composable.WTopBar
import com.vodafone.core.designSystem.theme.WeatherAppTheme
import com.vodafone.weatherutils.getWeatherIcon
import com.vodafone.weatherutils.toFormatedTemperatureText
import com.vodafone.weatherutils.toFormatedWindSpeedText

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    WeatherContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(

) {
    Scaffold(
        topBar = {
            Column {
                WTopBar(
                    title = "Today's Weather",
                    icon = painterResource(id = R.drawable.temperature),
                )
                WSearchField(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = "",
                    onValueChange = {},
                    placeHolder = "Type here a city name..",
                    enabled = true
                )
            }
        }
    ) { innerPadding ->
        WAnimationContent(
            state = false,
            content = {
                WAnimationContentState(
                    state = true,
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(top = innerPadding.calculateTopPadding() + 40.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CardCityWeather(
                                weatherIcon = painterResource(id = getWeatherIcon("Rain")),
                                temperature = 19.0,
                                cityName = "Cairo",
                                wind = 32.7,
                                weather = "Rain"
                            )
                            WFilledButton(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .height(62.dp),
                                label = "See next 5 days forecast! ->",
                                onClick = {})
                        }
                    },
                    placeholderContent = {
                        Box(
                            Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            WeatherPlaceholder()
                        }
                    }
                )
            },
            loadingContent = {
                Loading(state = false)
            },
            isError = false
        )
    }
}

@Composable
private fun CardCityWeather(
    weatherIcon: Painter,
    temperature: Double,
    cityName: String,
    wind: Double,
    weather: String
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(160.dp),
                painter = weatherIcon,
                contentDescription = stringResource(id = R.string.weather_placeholder),
                tint = Color.Unspecified
            )
            Text(
                text = temperature.toFormatedTemperatureText(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = cityName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = "Wind ${wind.toFormatedWindSpeedText()}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Text(
                text = weather,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

    }
}

@Composable
private fun WeatherPlaceholder() {
    Column(
        modifier = Modifier
            .padding(bottom = 200.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(300.dp),
            painter = painterResource(id = R.drawable.find_weather),
            contentDescription = stringResource(id = R.string.weather_placeholder),
            tint = Color.Unspecified
        )
        Text(
            text = stringResource(id = R.string.let_s_find_out_the_weather),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun AuthScreenPreview() {
    WeatherAppTheme {
        WeatherContent(

        )
    }
}
