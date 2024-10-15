package com.vodafone.core.designSystem.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF1F93EB)
val DarkGrey = Color(0xFF838C95)
val White = Color(0xFFFFFFFF)
val Shade = Color(0xFF000C1A)
val LightGrey = Color(0xFFEFF0F2)
val Red = Color(0xBEFF0000)

val LightColors = lightColorScheme(
    primary = Blue,
    secondary = Shade,
    onPrimary = White,
    tertiary = LightGrey,
    onTertiary = DarkGrey,
    background = White,
    onError = Red
)

val DarkColors = lightColorScheme(
    primary = Blue,
    secondary = Shade,
    onPrimary = White,
    tertiary = LightGrey,
    onTertiary = DarkGrey,
    background = White,
    onError = Red
)