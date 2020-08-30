package com.xacalet.moobies.presentation.movies.pager

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.IsWishlistedFlowUseCase
import com.xacalet.domain.usecase.ToggleWishlistUseCase
import com.xacalet.moobies.testutils.FlowTestUtil.sampleList
import com.xacalet.moobies.testutils.LiveDataTestUtil.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviePagerItemViewModelTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviePagerItemViewModel: MoviePagerItemViewModel

    @MockK
    private lateinit var getImageUrlUseCase: GetImageUrlUseCase

    @MockK
    private lateinit var isWishlistedFlowUseCase: IsWishlistedFlowUseCase

    @MockK
    private lateinit var toggleWishlistUseCase: ToggleWishlistUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(Dispatchers.Unconfined)

        moviePagerItemViewModel = MoviePagerItemViewModel(
            getImageUrlUseCase,
            isWishlistedFlowUseCase,
            toggleWishlistUseCase
        )
    }

    @Test
    fun testBackdropImageUrlLiveData() {
        // Given
        every {
            getImageUrlUseCase(
                imageWidth = 1000,
                filePath = "backdrop_file_path"
            )
        } returns "backdrop_file_path/1000"

        // When
        moviePagerItemViewModel.setBackdropImageParams(
            imageWidth = 1000,
            filePath = "backdrop_file_path"
        )

        // Then
        assertEquals(
            "backdrop_file_path/1000",
            moviePagerItemViewModel.backdropUrlImage.getOrAwaitValue()
        )
    }

    @Test
    fun testPosterImageUrlLiveData() {
        // Given
        every {
            getImageUrlUseCase(
                imageWidth = 1000,
                filePath = "poster_file_path"
            )
        } returns "poster_file_path/1000"

        // When
        moviePagerItemViewModel.setPosterImageParams(
            imageWidth = 1000,
            filePath = "poster_file_path"
        )

        // Then
        assertEquals(
            "poster_file_path/1000",
            moviePagerItemViewModel.posterUrlImage.getOrAwaitValue()
        )
    }

    @Test
    fun testIsWishlistedLiveData() {
        // Given
        every { isWishlistedFlowUseCase(100L) } returns flow { emit(true) }

        // When
        moviePagerItemViewModel.setId(100L)

        // Then
        assertEquals(true, moviePagerItemViewModel.isWishlisted.getOrAwaitValue())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testToggledWishlist() {
        // Given
        coEvery { toggleWishlistUseCase(100L) } returns true

        // When
        val list = mutableListOf<Boolean?>()
        moviePagerItemViewModel.toggledWishlist.sampleList(list)

        // Then
        moviePagerItemViewModel.toggleWishlist(100L)
        coVerify { toggleWishlistUseCase(100L) }
        assertEquals(null, list[0]) // Initial value
        assertEquals(true, list[1]) // toggled = true
        assertEquals(null, list[2]) // Workaround
    }
}
