package com.xacalet.moobies.presentation.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.xacalet.moobies.presentation.account.accountNavigation
import com.xacalet.moobies.presentation.home.HomeDirections
import com.xacalet.moobies.presentation.home.homeNavigation
import com.xacalet.moobies.presentation.search.searchNavigation
import com.xacalet.moobies.presentation.video.videoNavigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    Scaffold(bottomBar = { MainBottomBar(navController) }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            AnimatedNavHost(
                navController = navController,
                startDestination = HomeDirections.Root.route
            ) {
                homeNavigation(navController)
                searchNavigation()
                videoNavigation()
                accountNavigation()
            }
        }
    }
}
