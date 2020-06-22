package com.xacalet.moobies.presentation.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow


class MovieListViewModel @ViewModelInject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    val pagingFlow: Flow<PagingData<Movie>> = Pager(PagingConfig(20)) {
        PopularMoviePager(getPopularMoviesUseCase)
    }.flow.cachedIn(viewModelScope)
}
