package com.xacalet.moobies.db.datasource

import com.xacalet.data.datasource.UserRatingDataSource
import com.xacalet.moobies.db.dao.UserRatingDao
import com.xacalet.moobies.db.model.UserRatingDbModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRatingDataSourceImpl @Inject constructor(
    private val userRatingDao: UserRatingDao
) : UserRatingDataSource {

    override suspend fun addUserRating(showId: Long, stars: Byte) {
        userRatingDao.insert(UserRatingDbModel(showId, stars))
    }

    override suspend fun deleteUserRating(id: Long) {
        userRatingDao.delete(id)
    }

    override fun getUserRatingFlow(id: Long): Flow<Byte?> = userRatingDao.getByIdFlow(id)

    override suspend fun getUserRating(id: Long): Byte? = userRatingDao.getById(id)
}
