package com.xacalet.moobies.presentation.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.xacalet.domain.model.MovieDetails
import com.xacalet.moobies.presentation.ui.MoobiesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<MovieDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setId(args.movieId)
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        ComposeView(context = requireContext()).apply {
            setContent {
                viewModel.details.observeAsState().value.let { movie: MovieDetails? ->
                    if (movie != null) {
                        // TODO: Get dimensions later.
                        movie.posterPath.takeIf { !it.isNullOrBlank() }?.let { path ->
                            viewModel.setPosterImageParams(200, path)
                        }
                        // TODO: Get dimensions later.
                        movie.backdropPath.takeIf { !it.isNullOrBlank() }?.let { path ->
                            viewModel.setBackdropImageParams(1000, path)
                        }
                        MoobiesTheme {
                            MovieDetailsScreen(
                                movie = movie,
                                backdropImageUrl = viewModel.backdropUrlImage.observeAsState(),
                                posterImageUrl = viewModel.posterUrlImage.observeAsState(),
                                isWishlisted = viewModel.isWishlisted.observeAsState(false),
                                onWishlistToggled = { viewModel.toggleWishlist() }
                            )
                        }
                    }
                }
            }
        }
}
