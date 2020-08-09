package com.xacalet.domain.usecase

import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.PaginatedList
import com.xacalet.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetNowPlayingMoviesUseCase @Inject constructor(private val repository: MovieRepository) :
    GetMoviesUseCase {

    override suspend operator fun invoke(page: Int): PaginatedList<Movie> =
        withContext(Dispatchers.IO) {
            repository.getNowPlayingMovies(page)
        }
}
