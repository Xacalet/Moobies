package com.xacalet.data.datasource

import kotlinx.coroutines.flow.Flow

interface UserRatingDataSource {

    suspend fun addUserRating(showId: Long, stars: Byte)

    fun getUserRatingFlow(id: Long): Flow<Byte?>

    suspend fun getUserRating(id: Long): Byte?
}
