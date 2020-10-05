package com.xacalet.moobies.db.datasource

import com.xacalet.moobies.db.dao.UserRatingDao
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class UserRatingDataSourceImplTest {

    @MockK
    private val userRatingDao = mockk<UserRatingDao>()
    private val userRatingDataSource = UserRatingDataSourceImpl(userRatingDao)

    @Test
    fun `getTitlesByRating returns titles by rating`() = runBlocking {
        val dbResult: List<Long> = listOf(1L, 3L)
        coEvery { userRatingDao.getByRating(any()) } returns dbResult

        val result = userRatingDataSource.getTitlesByRating(6)

        Assert.assertEquals(dbResult, result)
    }
}
