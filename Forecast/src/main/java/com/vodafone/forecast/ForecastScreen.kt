package com.vodafone.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.vodafone.core.R
import com.vodafone.core.designSystem.composable.WTopBar

@Composable
fun ForecastScreen(
    cityName:String,
    onClickBack: () -> Unit
) {
    ForecastContent(
        cityName = cityName,
        onClickBack = onClickBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastContent(
    cityName:String,
    onClickBack: () -> Unit
) {
    Scaffold(topBar = {
        Column {
            WTopBar(
                title = "Forecast",
                icon = painterResource(id = R.drawable.temperature),
                backIcon = painterResource(id = R.drawable.arrow_left),
                backIconColor = MaterialTheme.colorScheme.secondary,
                onClickBack = onClickBack
            )

        }
    }) { innerPadding ->

    }
}
