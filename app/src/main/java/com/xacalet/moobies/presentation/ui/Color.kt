package com.xacalet.moobies.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightBlue700 = Color(0xFF0288D1)
val Blue400 = Color(0xFF42A5F5)
val Blue600 = Color(0xFF1E88E5)
val Yellow600 = Color(0xFFF3CE13)


val SecondaryText
    @Composable get() = run { if (isSystemInDarkTheme()) Gray400 else Gray600 }
val Gray400 = Color(0xFFBDBDBD)
val Gray600 = Color(0xFF757575)
val Gray800 = Color(0xFF424242)
val Gray900 = Color(0xFF212121)
