package com.vodafone.weather

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.R
import com.vodafone.core.designSystem.composable.Loading
import com.vodafone.core.designSystem.composable.WAlertDialog
import com.vodafone.core.designSystem.composable.WAnimationContent
import com.vodafone.core.designSystem.composable.WAnimationContentState
import com.vodafone.core.designSystem.composable.WFilledButton
import com.vodafone.core.designSystem.composable.WSearchField
import com.vodafone.core.designSystem.composable.WTopBar
import com.vodafone.core.designSystem.theme.WeatherAppTheme
import com.vodafone.weatherutils.getWeatherIcon
import com.vodafone.weatherutils.toFormatedTemperatureText
import com.vodafone.weatherutils.toFormatedWindSpeedText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel(),
    navigateToForecast: (String) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                WeatherUiEffect.ShowErrorToast -> {
                    Toast.makeText(
                        context, context.getString(R.string.error_message), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    WeatherContent(
        searchInput = viewModel.searchQuery,
        cityWeather = viewModel.cityWeather,
        isLoading = viewModel.isLoading,
        isError = viewModel.isError,
        showAlert = viewModel.showAlert,
        isCityNameCorrect = viewModel.isCityNameCorrect,
        navigateToForecast = navigateToForecast,
        changeShowAlertState = viewModel::changeAlertState,
        onClickTryAgain = viewModel::onClickTryAgain,
        onChangeSearchQuery = viewModel::onChangeSearchQuery,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(
    searchInput: String,
    cityWeather: CityWeatherUIState,
    isLoading: Boolean,
    isError: Boolean,
    isCityNameCorrect: Boolean,
    showAlert: Boolean,
    navigateToForecast: (String) -> Unit,
    onClickTryAgain: () -> Unit,
    changeShowAlertState: (Boolean) -> Unit,
    onChangeSearchQuery: (query: String) -> Unit,
) {
    val context = LocalContext.current
    Log.d("WeatherContent: ","${isModuleInstalled(context)}")
    if (showAlert) {
        WAlertDialog(
            onDismissRequest = { changeShowAlertState(false) },
            onConfirmation = {
                changeShowAlertState(false)
                navigateToForecast(searchInput)
            },
            dialogTitle = "Files Download",
            dialogText = "Files must be downloaded to open this feature!",
            icon = painterResource(id = R.drawable.download)
        )
    }
    Scaffold(topBar = {
        Column {
            WTopBar(
                title = "Today's Weather",
                icon = painterResource(id = R.drawable.temperature),
            )
            WSearchField(
                modifier = Modifier.padding(horizontal = 20.dp),
                value = searchInput,
                onValueChange = onChangeSearchQuery,
                placeHolder = "Type here a city name..",
                enabled = true
            )
        }
    }) { innerPadding ->
        WAnimationContent(
            state = isLoading,
            content = {
                WAnimationContentState(
                    state = !isLoading && !isError
                ) {
                    WAnimationContentState(
                        state = cityWeather.cityName.isNotEmpty() && isCityNameCorrect,
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(top = innerPadding.calculateTopPadding() + 40.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CardCityWeather(
                                    weatherIcon = painterResource(id = getWeatherIcon(cityWeather.weather)),
                                    temperature = cityWeather.temperature,
                                    cityName = cityWeather.cityName,
                                    wind = cityWeather.wind,
                                    weather = cityWeather.weather
                                )
                                WFilledButton(
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .height(62.dp),
                                    label = "See next 5 days forecast! ->",
                                    onClick = {
                                        if (isModuleInstalled(context)) {
                                            navigateToForecast(searchInput)
                                        } else {
                                            changeShowAlertState(true)
                                        }
                                    }
                                )
                            }
                        },
                        placeholderContent = {
                            Box(
                                Modifier
                                    .padding(horizontal = 20.dp)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                val placeholderText = if (isCityNameCorrect) {
                                    stringResource(id = R.string.let_s_find_out_the_weather)
                                } else {
                                    stringResource(id = R.string.city_not_found)
                                }
                                WeatherPlaceholder(placeholderText)
                            }
                        }
                    )
                }
            },
            loadingContent = {
                Loading(state = isLoading)
            },
            isError = isError,
            onClickTryAgain = onClickTryAgain
        )
    }
}

@Composable
private fun CardCityWeather(
    weatherIcon: Painter, temperature: Double, cityName: String, wind: Double, weather: String
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
                modifier = Modifier.size(160.dp),
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
private fun WeatherPlaceholder(placeholderText: String) {
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
            text = placeholderText,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun WeatherScreenPreview() {
    WeatherAppTheme {
        WeatherContent(
            searchInput = "",
            cityWeather = CityWeatherUIState(193.0, "Cairo", 37.8, "Rain"),
            isLoading = false,
            isError = false,
            isCityNameCorrect = false,
            changeShowAlertState = {},
            onClickTryAgain = {},
            onChangeSearchQuery = {},
            showAlert = true,
            navigateToForecast = {}
        )
    }
}