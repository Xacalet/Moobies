package com.xacalet.moobies.repository

import com.xacalet.moobies.data.datasource.UserRatingDataSource
import com.xacalet.moobies.data.repository.UserRatingRepositoryImpl
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRatingRepositoryImplTest {

    @MockK
    private val userRatingDataSource = mockk<UserRatingDataSource>()
    private val userRatingRepository = UserRatingRepositoryImpl(userRatingDataSource)

    @Test
    fun `getTitlesByRating returns titles by rating`() = runBlocking {
        val dataSourceResult: List<Long> = listOf(1L, 3L)
        coEvery { userRatingDataSource.getTitlesByRating(any()) } returns dataSourceResult

        val result = userRatingRepository.getTitlesByRating(6)

        assertEquals(dataSourceResult, result)
    }
}
