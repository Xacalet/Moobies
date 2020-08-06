package com.xacalet.moobies.presentation.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.usecase.GetMovieDetailsUseCase

class MovieDetailsViewModel @ViewModelInject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _id = MutableLiveData<Long>()

    private val _details: LiveData<MovieDetails> = _id.switchMap { id ->
        liveData(viewModelScope.coroutineContext) {
            emit(getMovieDetailsUseCase(id))
        }
    }

    val details: LiveData<MovieDetails> = _details

    fun start(id: Long) {
        _id.value = id
    }
}

