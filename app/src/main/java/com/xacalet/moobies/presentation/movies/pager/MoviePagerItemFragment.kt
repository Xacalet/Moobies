package com.xacalet.moobies.presentation.movies.pager

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.xacalet.domain.model.Movie
import com.xacalet.moobies.R
import com.xacalet.moobies.databinding.FragmentMoviePagerItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviePagerItemFragment : Fragment(R.layout.fragment_movie_pager_item) {

    private val viewModel by viewModels<MoviePagerItemViewModel>()

    private lateinit var binding: FragmentMoviePagerItemBinding

    private var movie: Movie? = null

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMoviePagerItemBinding.bind(view)

        (arguments?.getSerializable(MOVIE_KEY) as? Movie)?.let { movie = it }

        binding.title.text = movie?.title
        binding.subtitle.text = movie?.overview
        binding.wishlistButton.setOnClickListener {
            movie?.id?.let { id -> viewModel.toggleWishlist(id) }
        }

        viewModel.posterUrlImage.observe(viewLifecycleOwner, { url ->
            Glide.with(binding.posterImage.context).load(url).into(binding.posterImage)
        })

        viewModel.backdropUrlImage.observe(viewLifecycleOwner, { url ->
            Glide.with(binding.backdropImage.context).load(url).into(binding.backdropImage)
        })

        viewModel.isWishlisted.observe(viewLifecycleOwner, { isWishlisted ->
            binding.wishlistButton.isSelected = isWishlisted
        })

        lifecycleScope.launch {
            viewModel.toggledWishlist.collectLatest {
                it?.let { isWishlisted ->
                    val text =
                        if (isWishlisted) R.string.added_to_wishlist else R.string.removed_from_wishlist
                    Toast.makeText(view.context, text, Toast.LENGTH_SHORT).apply {
                        setGravity(Gravity.CENTER, 0, 0)
                        show()
                    }
                }
            }
        }

        movie?.id?.let { id -> viewModel.setId(id) }
        view.post {
            movie?.backdropPath.takeIf { !it.isNullOrBlank() }?.let { path ->
                viewModel.setBackdropImageParams(binding.backdropImage.width, path)
            }
            movie?.posterPath.takeIf { !it.isNullOrBlank() }?.let { path ->
                viewModel.setPosterImageParams(binding.posterImage.width, path)
            }
        }
    }

    fun updateLayoutProgress(offset: Int) {
        if (this::binding.isInitialized) {
            binding.movingContainer.translationX = offset.toFloat().times(0.75f)
        }
    }

    companion object {

        private const val MOVIE_KEY: String = "MOVIE_KEY"

        @JvmStatic
        fun newInstance(movie: Movie) =
            MoviePagerItemFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(MOVIE_KEY, movie)
                }
            }
    }
}