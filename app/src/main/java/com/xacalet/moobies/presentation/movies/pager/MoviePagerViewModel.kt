package com.xacalet.moobies.presentation.movies.pager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.PaginatedList
import com.xacalet.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject


@HiltViewModel
class MoviePagerViewModel @Inject constructor(
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