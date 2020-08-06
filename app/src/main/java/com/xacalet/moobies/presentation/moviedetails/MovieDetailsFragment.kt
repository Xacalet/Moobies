package com.xacalet.moobies.presentation.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.R
import com.xacalet.moobies.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var getImageUrlUseCase: GetImageUrlUseCase

    private val viewModel by viewModels<MovieDetailsViewModel>()

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailsBinding.bind(view)

        viewModel.start(args.movieId)

        viewModel.details.observe(viewLifecycleOwner, { movieDetails ->

            // Load data
            movieDetails.backdropPath?.let { backdropPath ->
                Glide.with(binding.backdropImage.context)
                    .load(getImageUrlUseCase.invoke(binding.backdropImage.width, backdropPath))
                    .centerCrop()
                    .into(binding.backdropImage)
            }
            movieDetails.posterPath?.let { posterPath ->
                Glide.with(binding.posterImage.context)
                    .load(getImageUrlUseCase.invoke(binding.posterImage.width, posterPath))
                    .centerCrop()
                    .into(binding.posterImage)
            }
            binding.movieTitle.text = movieDetails.originalTitle
            binding.overviewText.text = movieDetails.overview
            binding.yearAndDuratioText.text =
                "${movieDetails.releaseDate?.year} ${formatRuntime(movieDetails.runtime)}"
            binding.genreList.setGenres(movieDetails.genres)
            binding.ratingVoteAverage.text = "${movieDetails.voteAverage}/10"
            binding.ratingVoteCount.text = "${movieDetails.voteCount}"

        })
    }

    private fun formatRuntime(runtime: Int): String =
        "${runtime.div(60)}h ${runtime % 60}min"

}
