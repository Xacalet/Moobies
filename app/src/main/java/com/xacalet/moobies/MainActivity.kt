package com.xacalet.moobies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.xacalet.moobies.presentation.home.HomeScreen
import com.xacalet.moobies.presentation.home.HomeScreenViewModel
import com.xacalet.moobies.presentation.moviedetails.MovieDetailsScreen
import com.xacalet.moobies.presentation.moviedetails.MovieDetailsViewModel
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.userrating.UserRatingScreen
import com.xacalet.moobies.presentation.userrating.UserRatingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoobiesTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable(route = "home") {
                        val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
                        HomeScreen(
                            viewModel = homeScreenViewModel,
                            openShowDetail = { showId ->
                                navController.navigate("movieDetails/$showId")
                            }
                        )
                    }
                    composable(
                        route = "movieDetails/{showId}",
                        arguments = listOf(navArgument("showId") { type = NavType.LongType })
                    ) {
                        it.arguments?.getLong("showId")?.let { showId ->
                            val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
                            MovieDetailsScreen(
                                viewModel = movieDetailsViewModel,
                                openShowRating = { navController.navigate("userRating/$showId") }
                            )
                        }
                    }
                    composable(
                        route = "userRating/{showId}",
                        arguments = listOf(navArgument("showId") { type = NavType.LongType })
                    ) {
                        val userRatingViewModel: UserRatingViewModel = hiltViewModel()
                        UserRatingScreen(
                            viewModel = userRatingViewModel,
                            close = {
                                val previousRoute = navController.previousBackStackEntry?.destination?.route
                                if (previousRoute != null) {
                                    navController.popBackStack("movieDetails/{showId}", false)
                                } else {
                                    navController.popBackStack()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
