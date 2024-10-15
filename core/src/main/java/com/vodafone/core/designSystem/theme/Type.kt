package com.vodafone.core.designSystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = robotoFont,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = robotoFont,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = robotoFont,
        fontWeight = FontWeight.W500,
        fontSize = 10.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = robotoFont,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = robotoFont,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = robotoFont,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
    ),
)