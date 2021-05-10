package com.xacalet.moobies.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.navArgs
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.userrating.UserRatingScreen
import com.xacalet.moobies.presentation.userrating.UserRatingViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var userRatingViewModelFactory: UserRatingViewModelFactory

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
            ScreenContent()
        }
    }

    @ExperimentalMaterialApi
    @Composable
    private fun ScreenContent() {
        MoobiesTheme {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "movieDetails/{movieId}") {
                composable(
                    route = "movieDetails/{movieId}",
                    arguments = listOf(navArgument("movieId") {
                        type = NavType.LongType
                        defaultValue = args.movieId
                    })
                ) { backstackEntry ->
                    backstackEntry.arguments?.getLong("movieId")?.let { movieId ->
                        MovieDetailsScreen(movieId, navController)
                    }
                }
                composable(
                    route = "userRating/{showId}",
                    arguments = listOf(navArgument("showId") { type = NavType.LongType })
                ) { backstackEntry ->
                    backstackEntry.arguments?.getLong("showId")?.let { showId ->
                        val viewModel = userRatingViewModelFactory.create(showId)
                        UserRatingScreen(showId, navController, viewModel)
                    }
                }
            }
        }
    }
}
