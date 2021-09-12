package com.xacalet.moobies.presentation.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchNavigation() {
    composable(route = SearchDirections.Root.route) {
        SearchScreen()
    }
}
