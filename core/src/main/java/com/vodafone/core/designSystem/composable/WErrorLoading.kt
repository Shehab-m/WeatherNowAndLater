package com.vodafone.core.designSystem.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vodafone.core.R
import com.vodafone.core.designSystem.theme.WeatherAppTheme

@Composable
fun WErrorLoading(
    state: Boolean,
    size: Dp = 42.dp,
    onClickTryAgain: () -> Unit,
    onClickTryAgainText: String = "Try Again"
) {
    AnimatedVisibility(
        visible = state,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 500)
        ) + slideInVertically(),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 500)
        ) + slideOutVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.weather_error),
                contentDescription = "Error Image",
                modifier = Modifier.size(size),
                tint = MaterialTheme.colorScheme.onError
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "Error While Loading",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onError,
                textAlign = TextAlign.Center,
            )
            WFilledButton(
                label = onClickTryAgainText,
                onClick = onClickTryAgain,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .wrapContentWidth(),
                background = MaterialTheme.colorScheme.onError
            )
        }
    }
}

@Composable
@Preview
fun KErrorLoadingPreview() {
    WeatherAppTheme {
        WErrorLoading(state = true, onClickTryAgain = {})
    }
}
