package com.xacalet.domain.usecase

import com.xacalet.domain.di.IoDispatcher
import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.repository.MovieRepository
import com.xacalet.domain.repository.UserRatingRepository
import kotlinx.coroutines.*
import javax.inject.Inject


class GetMoviesByUserRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRatingRepository: UserRatingRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(userRating: Byte): List<MovieDetails> =
        withContext(ioDispatcher) {
            userRatingRepository.getTitlesByRating(userRating).map { id ->
                async { movieRepository.getMovieDetails(id) }
            }.awaitAll()
        }
}
