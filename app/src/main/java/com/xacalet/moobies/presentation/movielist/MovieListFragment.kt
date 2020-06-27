package com.xacalet.moobies.presentation.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var adapter: MovieListAdapter

    private val viewModel by viewModels<MovieListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        adapter = MovieListAdapter(view.context, GetImageUrlUseCase()) { id ->
            val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(id)
            findNavController().navigate(action)
        }
        movieListView.adapter = adapter.withLoadStateFooter(MoviesLoadStateAdapter(adapter))
        movieListView.addItemDecoration(MovieListItemDecoration())

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.pagingFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
