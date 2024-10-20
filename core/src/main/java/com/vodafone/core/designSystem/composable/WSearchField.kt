package com.vodafone.core.designSystem.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodafone.core.R
import com.vodafone.core.designSystem.theme.WeatherAppTheme
import com.vodafone.core.designSystem.theme.dimens

@Composable
fun WSearchField(
    @SuppressLint("ModifierParameter")
    modifier: Modifier = Modifier.height(MaterialTheme.dimens.textFieldHeight),
    onValueChange: (String) -> Unit,
    value: String = "",
    containerColor: Color = MaterialTheme.colorScheme.tertiary,
    borderColor: Color = MaterialTheme.colorScheme.tertiary,
    textColor: Color = MaterialTheme.colorScheme.secondary,
    placeHolderColor: Color =  MaterialTheme.colorScheme.onTertiary,
    leadingIconColor: Color = MaterialTheme.colorScheme.primary,
    placeHolder: String = "Search",
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    singleLine: Boolean = true,
    enabled: Boolean = true,
) {
    var fieldText by remember(value) { mutableStateOf(value) }
    val alpha = 0.5f
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        textStyle = textStyle.copy(fontWeight = FontWeight(400)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            focusedTextColor = textColor,
            focusedIndicatorColor = borderColor,
            focusedPlaceholderColor = placeHolderColor,
            focusedLeadingIconColor = leadingIconColor,
            unfocusedContainerColor = containerColor,
            unfocusedIndicatorColor = borderColor,
            unfocusedPlaceholderColor = placeHolderColor,
            unfocusedTextColor = textColor,
            unfocusedLeadingIconColor = leadingIconColor,
            cursorColor = textColor,
            disabledContainerColor = containerColor.copy(alpha = alpha),
            disabledIndicatorColor = borderColor.copy(alpha = alpha),
            disabledLeadingIconColor = leadingIconColor.copy(alpha = alpha),
            disabledPlaceholderColor = placeHolderColor.copy(alpha = alpha),
        ),
        shape = RoundedCornerShape(24.dp),
        value = fieldText,
        onValueChange = {
            fieldText = it
            onValueChange(it)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "Search icon",
            )
        },
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.labelMedium
            )
        },
        singleLine = singleLine,
        enabled = enabled
    )
}

@Preview
@Composable
fun KSearchFieldPreview() {
    WeatherAppTheme {
        WSearchField(
            value = "",
            onValueChange = {},
            placeHolder = "Search"
        )
    }
}