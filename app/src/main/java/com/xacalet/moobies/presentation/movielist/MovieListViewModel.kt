package com.xacalet.moobies.presentation.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetPopularMoviesUseCase
import javax.inject.Inject


class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    fun getPopularMovies(): LiveData<List<Movie>> =
        liveData {
            val list = getPopularMoviesUseCase()
            emit(list)
        }
}

