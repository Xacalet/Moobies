package com.xacalet.moobies.presentation.userrating

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.usecase.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserRatingViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userRatingViewModel: UserRatingViewModel

    @MockK
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @MockK
    private lateinit var getImageUrlUseCase: GetImageUrlUseCase

    @MockK
    private lateinit var getUserRatingUseCase: GetUserRatingUseCase

    @MockK
    private lateinit var addUserRatingUseCase: AddUserRatingUseCase

    @MockK
    private lateinit var deleteUserRatingUseCase: DeleteUserRatingUseCase

    @MockK
    private lateinit var getMoviesByUserRatingUseCase: GetMoviesByUserRatingUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(Dispatchers.Unconfined)

        userRatingViewModel = UserRatingViewModel(
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            getImageUrlUseCase = getImageUrlUseCase,
            getUserRatingUseCase = getUserRatingUseCase,
            addUserRatingUseCase = addUserRatingUseCase,
            deleteUserRatingUseCase = deleteUserRatingUseCase,
            getMoviesByUserRatingUseCase = getMoviesByUserRatingUseCase,
            ioDispatcher = TestCoroutineDispatcher()
        )
    }

    @Test
    fun `Setting rating returns a list of titles with the same rating`() {
        val movie1 = mockk<MovieDetails>()
        val movie2 = mockk<MovieDetails>()
        val movie3 = mockk<MovieDetails>()
        coEvery { movie1.id } returns 1L
        coEvery { movie2.id } returns 2L
        coEvery { movie3.id } returns 3L
        coEvery { getMoviesByUserRatingUseCase(6) } returns listOf(movie1, movie2, movie3)

        userRatingViewModel.setId(2L)
        userRatingViewModel.setUserRating(6)

        assertEquals(listOf(movie1, movie3), userRatingViewModel.otherTitlesWithSameRating.value)
    }
}