package com.xacalet.moobies.presentation.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VideoScreen() {
    // TODO: Implement Video screen.
    Surface(Modifier.fillMaxSize()) {
        Box {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Video"
            )
        }
    }
}
