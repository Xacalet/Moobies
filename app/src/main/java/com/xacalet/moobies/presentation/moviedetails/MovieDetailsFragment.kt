package com.xacalet.moobies.presentation.moviedetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.xacalet.moobies.R
import com.xacalet.moobies.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel by viewModels<MovieDetailsViewModel>()

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieDetailsBinding.bind(view)

        viewModel.setId(args.movieId)

        viewModel.details.observe(viewLifecycleOwner, { movieDetails ->

            movieDetails?.backdropPath.takeIf { !it.isNullOrBlank() }?.let { path ->
                viewModel.setBackdropImageParams(binding.backdropImage.width, path)
            }
            movieDetails?.posterPath.takeIf { !it.isNullOrBlank() }?.let { path ->
                viewModel.setPosterImageParams(binding.posterImage.width, path)
            }

            // Load data
            binding.movieTitle.text = movieDetails.originalTitle
            binding.overviewText.text = movieDetails.overview
            binding.yearAndDuratioText.text =
                "${movieDetails.releaseDate?.year} ${formatRuntime(movieDetails.runtime)}"
            binding.genreList.setGenres(movieDetails.genres)
            binding.ratingVoteAverage.text = "${movieDetails.voteAverage}/10"
            binding.ratingVoteCount.text = "${movieDetails.voteCount}"

        })

        viewModel.posterUrlImage.observe(viewLifecycleOwner, { url ->
            Glide.with(binding.posterImage.context).load(url).into(binding.posterImage)
        })

        viewModel.backdropUrlImage.observe(viewLifecycleOwner, { url ->
            Glide.with(binding.backdropImage.context).load(url).into(binding.backdropImage)
        })
    }

    private fun formatRuntime(runtime: Int): String = "${runtime.div(60)}h ${runtime % 60}min"
}
