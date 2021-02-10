package com.xacalet.moobies.db.datasource

import com.xacalet.moobies.data.datasource.UserRatingDataSource
import com.xacalet.moobies.db.dao.UserRatingDao
import com.xacalet.moobies.db.model.UserRatingDbModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRatingDataSourceImpl @Inject constructor(
    private val userRatingDao: UserRatingDao
) : UserRatingDataSource {

    override suspend fun addUserRating(showId: Long, stars: Int) {
        userRatingDao.insert(UserRatingDbModel(showId, stars))
    }

    override suspend fun deleteUserRating(id: Long) {
        userRatingDao.delete(id)
    }

    override fun getUserRatingFlow(id: Long): Flow<Int?> = userRatingDao.getByIdFlow(id)

    override suspend fun getUserRating(id: Long): Int? = userRatingDao.getById(id)

    override suspend fun getTitlesByRating(rating: Int): List<Long> =
        userRatingDao.getByRating(rating)
}
