package com.xacalet.moobies.presentation.movies.pager

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.PaginatedList
import com.xacalet.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.delay


class MoviePagerViewModel @ViewModelInject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val items: LiveData<PaginatedList<Movie>> = liveData {
        _isLoading.postValue(true)
        delay(2000)
        emit(getPopularMoviesUseCase(1))
        _isLoading.postValue(false)
    }
}