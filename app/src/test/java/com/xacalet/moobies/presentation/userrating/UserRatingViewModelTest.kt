package com.xacalet.moobies.presentation.userrating

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.xacalet.domain.usecase.AddUserRatingUseCase
import com.xacalet.domain.usecase.DeleteUserRatingUseCase
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.GetMovieDetailsUseCase
import com.xacalet.domain.usecase.GetMoviesByUserRatingUseCase
import com.xacalet.domain.usecase.GetUserRatingUseCase
import com.xacalet.moobies.presentation.components.SimpleShowListData
import com.xacalet.moobies.presentation.userrating.GetOtherRatedShowsState.Loading
import com.xacalet.moobies.presentation.userrating.GetOtherRatedShowsState.Result
import com.xacalet.moobies.testutils.LiveDataTestUtil.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
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

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(Dispatchers.Unconfined)

        val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)
        every { savedStateHandle.get<Long>("showId") } returns 42L

        userRatingViewModel = UserRatingViewModel(
            //id = 42L,
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            getImageUrlUseCase = getImageUrlUseCase,
            getUserRatingUseCase = getUserRatingUseCase,
            addUserRatingUseCase = addUserRatingUseCase,
            deleteUserRatingUseCase = deleteUserRatingUseCase,
            getMoviesByUserRatingUseCase = getMoviesByUserRatingUseCase,
            savedStateHandle = savedStateHandle,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun `setting id returns an instance of UserRatingUiModel`() = runBlockingTest {
        coEvery { getMovieDetailsUseCase(42L) } returns mockk {
            every { title } returns "The title"
            every { posterPath } returns "p_42"
        }
        coEvery { getImageUrlUseCase(any(), "p_42") } returns "http://images/p_42"
        coEvery { getUserRatingUseCase(42L) } returns 8

        val expectedResult = UserRatingUiModel(
            showId = 42L,
            showName = "The title",
            stars = 8,
            poserImageUrl = "http://images/p_42"
        )

        assertEquals(expectedResult, userRatingViewModel.data.getOrAwaitValue())
    }

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

        userRatingViewModel.fetchOtherRatedShows( 6, 90)

        assertEquals(Loading, userRatingViewModel.otherRatedShows.getOrAwaitValue())

        val expectedResult = Result((1..3).map {
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

    @Test
    fun `calling onRatingChanged runs addUserRatingUseCase`() {
        coEvery { addUserRatingUseCase(any(), any()) } just Runs

        userRatingViewModel.onRatingChanged(8)

        coVerifySequence { addUserRatingUseCase(42L, 8) }
    }

    @Test
    fun `calling onRatingRemoved runs deleteUserRatingUseCase`() {
        coEvery { deleteUserRatingUseCase(any()) } just Runs

        userRatingViewModel.onRatingRemoved()

        coVerifySequence { deleteUserRatingUseCase(42L) }
    }
}
