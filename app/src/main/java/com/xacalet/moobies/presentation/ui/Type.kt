package com.xacalet.moobies.presentation.ui

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xacalet.moobies.R

val AmazonEmber = FontFamily(
    Font(R.font.amazon_ember_light, FontWeight.Light),
    Font(R.font.amazon_ember_medium, FontWeight.Medium),
    Font(R.font.amazon_ember_regular, FontWeight.Normal)
)

val MoobiesTypography = Typography(
    h1 = TextStyle(
        fontSize = 128.sp,
        fontWeight = FontWeight.Light
    ),
    h2 = TextStyle(
        fontFamily = AmazonEmber,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = Yellow600
    ),
    h4 = TextStyle(
        fontFamily = AmazonEmber,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        color = Yellow600
    ),
    h5 = TextStyle(
        fontFamily = AmazonEmber,
        fontSize = 22.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Normal
    ),
    caption = TextStyle(
        fontFamily = AmazonEmber,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    )
)
