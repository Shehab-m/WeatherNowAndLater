package com.vodafone.core.designSystem.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vodafone.core.R
import com.vodafone.core.designSystem.theme.WeatherAppTheme

@SuppressLint("UnrememberedMutableState")
@ExperimentalMaterial3Api
@Composable
fun WTopBar(
    modifier: Modifier = Modifier,
    title: String,
    topPadding: PaddingValues = PaddingValues(0.dp),
    titleColor: Color = MaterialTheme.colorScheme.secondary,
    horizontalPadding: Dp = 20.dp,
    icon: Painter? = null,
    iconColor: Color = Color.Unspecified,
    backIcon: Painter? = null,
    backIconColor: Color = Color.Unspecified,
    onClickBack: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = horizontalPadding)
            .fillMaxWidth()
            .padding(top = topPadding.calculateTopPadding()),
    ) {
        backIcon?.let {
            IconButton(modifier = Modifier.size(26.dp),onClick = onClickBack) {
                Icon(
                    modifier = Modifier,
                    painter = it,
                    contentDescription = stringResource(R.string.back_icon),
                    tint = backIconColor
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = titleColor,
            )
            icon?.let {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(26.dp),
                    painter = it,
                    contentDescription = stringResource(R.string.header_icon),
                    tint = iconColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TTopBarPreview() {
    WeatherAppTheme {
        WTopBar(
            title = "Github Repositories",
            icon = painterResource(id = R.drawable.weather_error),
            iconColor = MaterialTheme.colorScheme.secondary,
            backIcon = painterResource(id = R.drawable.arrow_left),
            backIconColor = MaterialTheme.colorScheme.primary
        )
    }
}