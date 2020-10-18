package com.xacalet.moobies.presentation.userrating

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.xacalet.domain.usecase.*
import com.xacalet.moobies.presentation.components.SimpleShowListData
import com.xacalet.moobies.presentation.userrating.GetOtherRatedShowsState.Loading
import com.xacalet.moobies.presentation.userrating.GetOtherRatedShowsState.Result
import com.xacalet.moobies.testutils.LiveDataTestUtil.getOrAwaitValue
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

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
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `setting id returns an instance of UserRatingUiModel`() = runBlockingTest {
        coEvery { getMovieDetailsUseCase(42L) } returns mockk {
            every { title } returns "The title"
            every { posterPath } returns "p_42"
        }
        coEvery { getImageUrlUseCase(any(), "p_42") } returns "http://images/p_42"
        coEvery { getUserRatingUseCase(42L) } returns 8

        userRatingViewModel.setId(42L)

        val expectedResult = UserRatingUiModel(
            showId = 42L,
            showName = "The title",
            stars = 8,
            poserImageUrl = "http://images/p_42"
        )

        assertEquals(expectedResult, userRatingViewModel.data.getOrAwaitValue())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `setting rating returns a list of titles with the same rating`() = runBlockingTest {
        coEvery { getMoviesByUserRatingUseCase(6) } returns (1..3).map {
            mockk {
                every { id } returns it.toLong()
                every { posterPath } returns "p_$it"
                every { title } returns "title_$it"
                every { releaseDate } returns LocalDate.of(2000 + it, 1, 1)
            }
        }
        (1..3).forEach {
            coEvery { getImageUrlUseCase(any(), "p_$it") } returns "http://images/p_$it"
        }

        userRatingViewModel.fetchOtherRatedShows(2L, 6, 90)

        assertEquals(Loading, userRatingViewModel.otherRatedShows.getOrAwaitValue())

        val expectedResult = Result(listOf(1, 3).map {
            SimpleShowListData(
                id = it.toLong(),
                imageUrl = "http://images/p_$it",
                title = "title_$it",
                year = 2000.plus(it).toString()
            )
        })

        // If live data emits multiple values,
        Thread.sleep(1000)

        assertEquals(expectedResult, userRatingViewModel.otherRatedShows.getOrAwaitValue())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `calling onRatingChanged runs addUserRatingUseCase`() {
        coEvery { addUserRatingUseCase(any(), any()) } just Runs

        userRatingViewModel.setId(42L)
        userRatingViewModel.onRatingChanged(8)

        coVerifySequence { addUserRatingUseCase(42L, 8) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `calling onRatingRemoved runs deleteUserRatingUseCase`() {
        coEvery { deleteUserRatingUseCase(any()) } just Runs

        userRatingViewModel.setId(42L)
        userRatingViewModel.onRatingRemoved()

        coVerifySequence { deleteUserRatingUseCase(42L) }
    }
}
