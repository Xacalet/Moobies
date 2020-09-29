package com.xacalet.data.datasource

import kotlinx.coroutines.flow.Flow

interface UserRatingDataSource {

    suspend fun addUserRating(showId: Long, stars: Byte)

    suspend fun deleteUserRating(id: Long)

    fun getUserRatingFlow(id: Long): Flow<Byte?>

    suspend fun getUserRating(id: Long): Byte?
}
