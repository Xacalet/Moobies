package com.xacalet.moobies.presentation.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.usecase.GetMovieDetailsUseCase

class MovieDetailsViewModel @ViewModelInject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    fun getMovieDetails(id: Long): LiveData<Result<MovieDetails>> =
        liveData {
            val list = kotlin.runCatching {
                getMovieDetailsUseCase(id)
            }
            emit(list)
        }
}

