package com.xacalet.moobies.presentation.movielist

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_list.*

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var adapter: MovieListAdapter

    private val viewModel by viewModels<MovieListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListView.layoutManager = GridLayoutManager(view.context, 3)
        adapter = MovieListAdapter(view.context, GetImageUrlUseCase()) { id ->
            val action =
                MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(id)
            this.findNavController().navigate(action)
        }
        movieListView.adapter = adapter
        movieListView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean = false

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                TODO("Not yet implemented")
            }


        })

        viewModel.getPopularMovies().observe(viewLifecycleOwner, Observer { movies ->
            adapter.setMovies(movies)
        })
    }
}
