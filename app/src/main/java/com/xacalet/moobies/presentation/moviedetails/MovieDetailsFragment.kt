package com.xacalet.moobies.presentation.moviedetails

import android.content.Context
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
import com.xacalet.moobies.MoobiesApplication
import com.xacalet.moobies.R
import com.xacalet.moobies.di.ViewModelFactory
import com.xacalet.moobies.presentation.moviedetails.di.MovieDetailsComponent
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.movie_list_item.view.*
import javax.inject.Inject

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private lateinit var movieDetailsComponent: MovieDetailsComponent

    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var getImageUrlUseCase: GetImageUrlUseCase

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MovieDetailsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        // Creates the instance of MovieListComponent
        movieDetailsComponent =
            (requireActivity().application as MoobiesApplication).appComponent.movieDetailsComponent()
                .create()

        // Injects the created component into this fragment
        movieDetailsComponent.inject(this)

        super.onAttach(context)
    }

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
                }
                .onFailure {
                    context?.let { context ->
                        Toast.makeText(context, "Couldn't get movie details", Toast.LENGTH_LONG).show()
                    }
                    this.findNavController().popBackStack()
                }

        })
    }

    private fun formatRuntime(runtime: Int): String =
        "${runtime.div(60)}h ${runtime % 60}min"

}
