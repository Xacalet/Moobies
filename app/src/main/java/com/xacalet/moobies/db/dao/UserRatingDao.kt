package com.xacalet.moobies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xacalet.moobies.db.model.UserRatingDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRatingDao {

    @Query("SELECT stars FROM user_rating WHERE id = :id")
    fun getByIdFlow(id: Long): Flow<Int?>

    @Query("SELECT stars FROM user_rating WHERE id = :id")
    fun getById(id: Long): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rating: UserRatingDbModel)

    @Query("DELETE FROM user_rating WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT id FROM user_rating WHERE stars = :rating")
    fun getByRating(rating: Int): List<Long>
}
