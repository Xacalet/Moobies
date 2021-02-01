package com.xacalet.moobies.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.viewModel
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.navArgs
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import com.xacalet.moobies.presentation.userrating.UserRatingScreen
import com.xacalet.moobies.presentation.userrating.UserRatingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
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
                            val viewModel: MovieDetailsViewModel = viewModel(
                                factory = HiltViewModelFactory(
                                    AmbientContext.current,
                                    backstackEntry
                                )
                            )
                            viewModel.setId(movieId)
                            MovieDetailsScreen(movieId, navController, viewModel)
                        }
                    }
                    composable(
                        route = "userRating/{movieId}",
                        arguments = listOf(navArgument("movieId") { type = NavType.LongType })
                    ) { backstackEntry ->
                        backstackEntry.arguments?.getLong("movieId")?.let { showId ->
                            val viewModel: UserRatingViewModel = viewModel(
                                factory = HiltViewModelFactory(
                                    AmbientContext.current,
                                    backstackEntry
                                )
                            )
                            viewModel.setId(showId)
                            UserRatingScreen(showId, navController, viewModel)
                        }
                    }
                }
            }
        }
    }
}
