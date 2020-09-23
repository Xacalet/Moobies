package com.xacalet.moobies.presentation.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient
import com.google.android.material.composethemeadapter.createMdcTheme

@Composable
fun MoobiesTheme(
    content: @Composable () -> Unit
) {
    val context = ContextAmbient.current
    val (colors, type, shapes) = createMdcTheme(context)
    MaterialTheme(
        colors = colors ?: MaterialTheme.colors,
        typography = type ?: MaterialTheme.typography,
        shapes = shapes ?: MaterialTheme.shapes,
        content = content
    )
}
