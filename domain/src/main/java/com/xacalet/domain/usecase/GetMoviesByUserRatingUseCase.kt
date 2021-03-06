package com.xacalet.domain.usecase

import com.xacalet.domain.di.IoDispatcher
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.repository.MovieRepository
import com.xacalet.domain.repository.UserRatingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetMoviesByUserRatingUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userRatingRepository: UserRatingRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(userRating: Int): List<MovieDetails> =
        withContext(ioDispatcher) {
            userRatingRepository.getTitlesByRating(userRating).map { id ->
                async { movieRepository.getMovieDetails(id) }
            }.awaitAll()
        }
}
