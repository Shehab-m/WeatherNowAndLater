package com.vodafone.forecast

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.vodafone.core.designSystem.theme.WeatherAppTheme
import com.vodafone.core.utils.Constants.CITY_NAME_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cityName = intent.getStringExtra(CITY_NAME_KEY) ?: ""
        setContent {
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ForecastScreen(
                            cityName = cityName,
                            onClickBack = { finish() }
                        )
                    }
                }
            }
        }
    }

}