package com.xacalet.moobies.presentation.account

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.accountNavigation() {
    composable(route = AccountDirections.Root.route) {
        AccountScreen()
    }
}
