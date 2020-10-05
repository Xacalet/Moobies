package com.xacalet.domain.usecase

import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.repository.MovieRepository
import com.xacalet.domain.repository.UserRatingRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMoviesByUserRatingUseCaseTest {

    private val userRatingRepository = mockk<UserRatingRepository>()
    private val movieRepository = mockk<MovieRepository>()

    @ExperimentalCoroutinesApi
    private val getMoviesByUserRatingUseCase = GetMoviesByUserRatingUseCase(
        userRatingRepository = userRatingRepository,
        movieRepository = movieRepository,
        ioDispatcher = TestCoroutineDispatcher()
    )

    @Test
    @ExperimentalCoroutinesApi
    fun `returns a list of titles with the same user rating as the one provided`() =
        runBlockingTest {
            val movie1 = mockk<MovieDetails>()
            val movie2 = mockk<MovieDetails>()
            coEvery { userRatingRepository.getTitlesByRating(6) } returns listOf(1L, 2L)
            coEvery { movieRepository.getMovieDetails(1L) } returns movie1
            coEvery { movieRepository.getMovieDetails(2L) } returns movie2

            val result = getMoviesByUserRatingUseCase(6)

            assertEquals(listOf(movie1, movie2), result)
        }
}
