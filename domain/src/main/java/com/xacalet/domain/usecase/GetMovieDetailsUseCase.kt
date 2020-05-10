package com.xacalet.domain.usecase

import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(id: Long): MovieDetails =
        withContext(Dispatchers.IO) {
            repository.getMovieDetails(id)
        }
}
