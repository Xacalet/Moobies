package com.xacalet.moobies.data.repository

import com.xacalet.domain.repository.UserRatingRepository
import com.xacalet.moobies.data.datasource.UserRatingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRatingRepositoryImpl @Inject constructor(
    private val userRatingDataSource: UserRatingDataSource
) : UserRatingRepository {

    override suspend fun addUserRating(id: Long, stars: Byte) =
        userRatingDataSource.addUserRating(id, stars)

    override suspend fun deleteUserRating(id: Long) =
        userRatingDataSource.deleteUserRating(id)

    override fun getUserRatingFlow(id: Long): Flow<Byte?> =
        userRatingDataSource.getUserRatingFlow(id)

    override suspend fun getUserRating(id: Long): Byte? = userRatingDataSource.getUserRating(id)

    override suspend fun getTitlesByRating(rating: Byte): List<Long> =
        userRatingDataSource.getTitlesByRating(rating)
}
