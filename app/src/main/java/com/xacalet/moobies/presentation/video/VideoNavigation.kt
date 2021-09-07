package com.xacalet.moobies.presentation.video

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.videoNavigation() {
    composable(route = VideoDirections.Root.route) {
        VideoScreen()
    }
}
