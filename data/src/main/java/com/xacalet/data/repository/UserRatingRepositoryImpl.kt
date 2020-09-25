package com.xacalet.data.repository

import com.xacalet.data.datasource.UserRatingDataSource
import com.xacalet.domain.repository.UserRatingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRatingRepositoryImpl @Inject constructor(
    private val userRatingDataSource: UserRatingDataSource
) : UserRatingRepository {

    override suspend fun addUserRating(id: Long, stars: Byte) =
        userRatingDataSource.addUserRating(id, stars)

    override fun getUserRatingFlow(id: Long): Flow<Byte?> = userRatingDataSource.getUserRatingFlow(id)

    override suspend fun getUserRating(id: Long): Byte? = userRatingDataSource.getUserRating(id)
}
