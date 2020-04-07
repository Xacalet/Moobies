package com.xacalet.moobies.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xacalet.domain.usecase.GetPopularMoviesUseCase
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class MovieListViewModelFactory @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MovieListViewModel::class.java) ->
                MovieListViewModel(getPopularMoviesUseCase) as T
            else ->
                throw IllegalArgumentException("ViewModel Not Found")
        }
}
