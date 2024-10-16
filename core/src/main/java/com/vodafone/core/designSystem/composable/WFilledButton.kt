package com.vodafone.core.designSystem.composable

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.vodafone.core.designSystem.theme.WeatherAppTheme

@Composable
fun WFilledButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    background: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = contentColor,
        )
    ) {
        Text(
            text = label,
            style = textStyle,
            color = contentColor,
        )
    }
}

@Composable
@Preview
fun KFilledButtonPreview() {
    WeatherAppTheme {
        WFilledButton(label = "Try Again", onClick = {})
    }
}
