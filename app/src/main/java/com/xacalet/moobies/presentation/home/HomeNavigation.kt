package com.xacalet.moobies.presentation.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.xacalet.moobies.presentation.navigation.CommonDirections
import com.xacalet.moobies.presentation.showdetails.MovieDetailsScreen
import com.xacalet.moobies.presentation.userrating.UserRatingScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeNavigation(navController: NavHostController) {
    navigation(startDestination = HomeDirections.Home.route, route = HomeDirections.Root.route) {
        composable(route = HomeDirections.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                openShowDetail = { showId ->
                    navController.navigate(CommonDirections.ShowDetails.createDestination(showId))
                }
            )
        }
        composable(
            route = CommonDirections.ShowDetails.route,
            arguments = CommonDirections.ShowDetails.arguments
        ) {
            MovieDetailsScreen(
                viewModel = hiltViewModel(),
                openShowRating = { showId ->
                    navController.navigate(
                        CommonDirections.UserRating.createDestination(showId)
                    )
                }
            )
        }
        composable(
            route = CommonDirections.UserRating.route,
            arguments = CommonDirections.UserRating.arguments,
            enterTransition = { slideInVertically(tween(700)) { 1800 } },
            exitTransition = { slideOutVertically(tween(700)) { 1800 } },
        ) {
            UserRatingScreen(
                viewModel = hiltViewModel(),
                close = {
                    navController.popBackStack(
                        CommonDirections.UserRating.route,
                        inclusive = true
                    )
                }
            )
        }
    }
}
