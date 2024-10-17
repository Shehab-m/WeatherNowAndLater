package com.vodafone.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.designSystem.theme.WeatherAppTheme

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {

}

@Composable
fun WeatherContent(

) {

}


@Composable
@Preview
fun AuthScreenPreview() {
    WeatherAppTheme {
        WeatherContent(

        )
    }
}
