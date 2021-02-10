package com.xacalet.moobies.data.datasource

import kotlinx.coroutines.flow.Flow

interface UserRatingDataSource {

    suspend fun addUserRating(showId: Long, stars: Int)

    suspend fun deleteUserRating(id: Long)

    fun getUserRatingFlow(id: Long): Flow<Int?>

    suspend fun getUserRating(id: Long): Int?

    suspend fun getTitlesByRating(rating: Int): List<Long>
}
