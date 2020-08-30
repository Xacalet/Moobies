package com.xacalet.moobies.presentation.movielist

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.IsWishlistedFlowUseCase
import com.xacalet.moobies.R
import com.xacalet.moobies.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var adapter: MovieListAdapter

    private val viewModel by viewModels<MovieListViewModel>()

    @Inject
    lateinit var isWishlistedFlowUseCase: IsWishlistedFlowUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMovieListBinding.bind(view)

        binding.movieListView.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        val onItemClick: (Long) -> Unit = { id ->
            findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(id))
        }
        val onWishlistButtonClick: (Long) -> Unit = { id ->
            viewModel.toggleWishlist(id)
        }
        adapter = MovieListAdapter(
            view.context,
            GetImageUrlUseCase(),
            onItemClick,
            onWishlistButtonClick,
            isWishlistedFlowUseCase
        )
        binding.movieListView.adapter = adapter.withLoadStateFooter(MoviesLoadStateAdapter(adapter))
        binding.movieListView.addItemDecoration(MovieListItemDecoration())

        lifecycleScope.launchWhenCreated {
            viewModel.pagingFlow.collectLatest {
                adapter.submitData(it)
            }
        }

        viewModel.addedToWishlist.observe(viewLifecycleOwner, { isWishlisted ->
            val text = if (isWishlisted) R.string.added_to_wishlist else R.string.removed_from_wishlist
            Toast.makeText(view.context, text, Toast.LENGTH_SHORT).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }
        })
    }
}
