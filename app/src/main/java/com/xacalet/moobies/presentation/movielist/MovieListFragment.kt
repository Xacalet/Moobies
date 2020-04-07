package com.xacalet.moobies.presentation.movielist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xacalet.moobies.MoobiesApplication
import com.xacalet.moobies.R
import com.xacalet.moobies.presentation.movielist.di.MovieListComponent
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject


class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var movieListComponent: MovieListComponent

    private lateinit var adapter: MovieListAdapter

    @Inject
    lateinit var viewModelFactory: MovieListViewModelFactory

    private val viewModel by viewModels<MovieListViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        // Creates the instance of MovieListComponent
        movieListComponent =
            (requireActivity().application as MoobiesApplication).appComponent.loginComponent()
                .create()

        // Injects the created component into this fragment
        movieListComponent.inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListView.layoutManager = GridLayoutManager(view.context, 3)
        adapter = MovieListAdapter(view.context)
        movieListView.adapter = adapter

        viewModel.getPopularMovies().observe(viewLifecycleOwner, Observer { movies ->
            adapter.setMovies(movies)
        })
    }
}
