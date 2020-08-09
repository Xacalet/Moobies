package com.xacalet.moobies.presentation.movies.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.R
import com.xacalet.moobies.databinding.FragmentMoviePagerItemBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MoviePagerItemFragment : Fragment(R.layout.fragment_movie_pager_item) {

    @Inject
    lateinit var getImageUrlUseCase: GetImageUrlUseCase

    private lateinit var binding: FragmentMoviePagerItemBinding

    private var movie: Movie? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMoviePagerItemBinding.bind(view)

        (arguments?.getSerializable(MOVIE_KEY) as? Movie)?.let { movie = it }

        binding.title.text = movie?.title
        binding.subtitle.text = movie?.overview

        view.post {
            movie?.backdropPath?.let { backdropPath ->
                Glide.with(binding.backdropImage.context)
                    .load(getImageUrlUseCase.invoke(binding.backdropImage.width, backdropPath))
                    .into(binding.backdropImage)
            }
            movie?.posterPath?.let { posterPath ->
                Glide.with(binding.posterImage.context)
                    .load(getImageUrlUseCase.invoke(binding.posterImage.width, posterPath))
                    .centerCrop()
                    .into(binding.posterImage)
            }
        }
    }

    fun updateLayoutProgress(offset: Int) {
        if (this::binding.isInitialized) {
            binding.posterImage.translationX = offset.toFloat().times(0.75f)
            binding.title.translationX = offset.toFloat().times(0.75f)
            binding.subtitle.translationX = offset.toFloat().times(0.75f)
        }
    }

    companion object {

        private const val MOVIE_KEY: String = "MOVIE_KEY"

        @JvmStatic
        fun newInstance(movie: Movie) =
            MoviePagerItemFragment().apply {
                arguments = Bundle().apply {
                    // TODO: Move this to constant
                    putSerializable(MOVIE_KEY, movie)
                }
            }
    }
}