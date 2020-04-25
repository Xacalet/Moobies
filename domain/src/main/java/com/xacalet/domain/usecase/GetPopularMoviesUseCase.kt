package com.xacalet.domain.usecase

import com.xacalet.domain.model.Movie
import com.xacalet.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetPopularMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun execute(): List<Movie> =
        withContext(Dispatchers.IO) {
            repository.getPopularMovies()
        }
}
