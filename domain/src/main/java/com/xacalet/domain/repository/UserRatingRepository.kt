package com.xacalet.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRatingRepository {

    suspend fun addUserRating(id: Long, stars: Byte)

    fun getUserRatingFlow(id: Long): Flow<Byte?>

    suspend fun getUserRating(id: Long): Byte?
}
