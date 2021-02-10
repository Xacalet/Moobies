package com.xacalet.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRatingRepository {

    suspend fun addUserRating(id: Long, stars: Int)

    suspend fun deleteUserRating(id: Long)

    fun getUserRatingFlow(id: Long): Flow<Int?>

    suspend fun getUserRating(id: Long): Int?

    suspend fun getTitlesByRating(rating: Int): List<Long>
}
