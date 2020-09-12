package com.xacalet.moobies.presentation.movies.pager

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionManager
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.google.android.material.transition.MaterialFadeThrough
import com.xacalet.moobies.R
import com.xacalet.moobies.databinding.FragmentMoviePagerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoviePagerFragment : Fragment(R.layout.fragment_movie_pager) {

    private val viewModel by viewModels<MoviePagerViewModel>()

    private lateinit var pagerAdapter: MoviePagerAdapter

    private lateinit var binding: FragmentMoviePagerBinding

    private lateinit var onPageChangeCallback: OnPageChangeCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMoviePagerBinding.bind(view)
        pagerAdapter = MoviePagerAdapter(this)
        onPageChangeCallback = OnPageChangeCallback(binding.latestMoviesPager, pagerAdapter)

        binding.latestMoviesPager.offscreenPageLimit = 1
        binding.latestMoviesPager.adapter = pagerAdapter
        binding.latestMoviesPager.registerOnPageChangeCallback(onPageChangeCallback)
        binding.animationView.addValueCallback(
            // Apply color filter over every animation layer.
            KeyPath("**"),
            LottieProperty.COLOR_FILTER,
            {
                PorterDuffColorFilter(
                    resources.getColor(R.color.imdbGold, null),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        )

        viewModel.items.observe(viewLifecycleOwner, { list ->
            pagerAdapter.setItems(list.results)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            TransitionManager.beginDelayedTransition(binding.container, MaterialFadeThrough())
            binding.latestMoviesPager.isVisible = !isLoading
            binding.animationView.isVisible = isLoading
        })
    }

    class OnPageChangeCallback(
        private val viewPager: ViewPager2,
        private val pagerAdapter: MoviePagerAdapter
    ) : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            // Animates the current page
            pagerAdapter.getFragmentAt(position)
                ?.updateLayoutProgress(-positionOffsetPixels)

            // Animates the next page (if exists)
            pagerAdapter.getFragmentAt(position + 1)
                ?.updateLayoutProgress(viewPager.width - positionOffsetPixels)
        }
    }
}
