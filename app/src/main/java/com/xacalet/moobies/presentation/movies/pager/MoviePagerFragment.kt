package com.xacalet.moobies.presentation.movies.pager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.xacalet.domain.model.Movie
import com.xacalet.moobies.R
import com.xacalet.moobies.databinding.FragmentMoviePagerBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference


@AndroidEntryPoint
class MoviePagerFragment : Fragment(R.layout.fragment_movie_pager) {

    private val viewModel by viewModels<MoviePagerViewModel>()

    private lateinit var pagerAdapter: PageAdapter

    private lateinit var binding: FragmentMoviePagerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMoviePagerBinding.bind(view)
        pagerAdapter = PageAdapter(this)
        with(binding) {
            latestMoviesPager.adapter = pagerAdapter
            latestMoviesPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
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
                        ?.updateLayoutProgress(latestMoviesPager.width - positionOffsetPixels)
                }
            })
        }

        viewModel.items.observe(viewLifecycleOwner, { list ->
            pagerAdapter.setItems(list.results)
        })
    }

    class PageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment.childFragmentManager, fragment.lifecycle) {

        private val fragmentManager = fragment.childFragmentManager

        private val fragments: MutableMap<Int, WeakReference<MoviePagerItemFragment>> =
            mutableMapOf()

        private val items: MutableList<Movie> = mutableListOf()

        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment =
            MoviePagerItemFragment.newInstance(items[position]).apply {
                fragments[position] = WeakReference(this)
            }

        override fun onBindViewHolder(
            holder: FragmentViewHolder,
            position: Int,
            payloads: MutableList<Any>
        ) {
            super.onBindViewHolder(holder, position, payloads)
            if (!fragments.containsKey(position)) {
                (fragmentManager.findFragmentByTag("f" + items[position].id) as? MoviePagerItemFragment)?.apply {
                    fragments[position] = WeakReference(this)
                }
            }
        }

        fun getFragmentAt(position: Int): MoviePagerItemFragment? = fragments[position]?.get()

        fun setItems(items: List<Movie>) {
            this.items.clear()
            this.items.addAll(items)
            notifyDataSetChanged()
        }
    }
}