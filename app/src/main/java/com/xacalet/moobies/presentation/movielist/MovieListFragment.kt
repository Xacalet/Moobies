package com.xacalet.moobies.presentation.movielist

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.moobies.MoobiesApplication
import com.xacalet.moobies.R
import com.xacalet.moobies.di.ViewModelFactory
import com.xacalet.moobies.presentation.moviedetails.MovieDetailsFragment
import com.xacalet.moobies.presentation.movielist.di.MovieListComponent
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject


class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var movieListComponent: MovieListComponent

    private lateinit var adapter: MovieListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MovieListViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        // Creates the instance of MovieListComponent
        movieListComponent =
            (requireActivity().application as MoobiesApplication).appComponent.movieListComponent()
                .create()

        // Injects the created component into this fragment
        movieListComponent.inject(this)

        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListView.layoutManager = GridLayoutManager(view.context, 3)
        adapter = MovieListAdapter(view.context, GetImageUrlUseCase()) { id ->
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(id)
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
