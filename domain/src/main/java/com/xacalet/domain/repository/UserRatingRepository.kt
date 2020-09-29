package com.xacalet.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRatingRepository {

    suspend fun addUserRating(id: Long, stars: Byte)

    suspend fun deleteUserRating(id: Long)

    fun getUserRatingFlow(id: Long): Flow<Byte?>

    suspend fun getUserRating(id: Long): Byte?
}
