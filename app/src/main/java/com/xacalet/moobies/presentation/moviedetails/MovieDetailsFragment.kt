package com.xacalet.moobies.presentation.moviedetails

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var getImageUrlUseCase: GetImageUrlUseCase

    private val viewModel by viewModels<MovieDetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieDetails(args.movieId).observe(viewLifecycleOwner, Observer { result ->
            result
                .onSuccess { movieDetails ->
                    // Load data
                    movieDetails.backdropPath?.let { backdropPath ->
                        Glide.with(backdropImage.context)
                            .load(getImageUrlUseCase.invoke(backdropImage.width, backdropPath))
                            .centerCrop()
                            .into(backdropImage)
                    }
                    movieDetails.posterPath?.let { posterPath ->
                        Glide.with(posterImage.context)
                            .load(getImageUrlUseCase.invoke(posterImage.width, posterPath))
                            .centerCrop()
                            .into(posterImage)
                    }
                    movieTitle.text = movieDetails.originalTitle
                    overviewText.text = movieDetails.overview
                    yearAndDuratioText.text =
                        "${movieDetails.releaseDate?.year} ${formatRuntime(movieDetails.runtime)}"
                    genreList.setGenres(movieDetails.genres)
                    ratingVoteAverage.text = "${movieDetails.voteAverage}/10"
                    ratingVoteCount.text = "${movieDetails.voteCount}"
                }
                .onFailure {
                    context?.let { context ->
                        Toast.makeText(context, "Couldn't get movie details", Toast.LENGTH_LONG)
                            .show()
                    }
                    this.findNavController().popBackStack()
                }

        })
    }

    private fun formatRuntime(runtime: Int): String =
        "${runtime.div(60)}h ${runtime % 60}min"

}
