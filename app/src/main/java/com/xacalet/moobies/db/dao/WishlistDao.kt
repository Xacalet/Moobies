package com.xacalet.moobies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xacalet.moobies.db.model.IsWishlistedDbModel
import com.xacalet.moobies.db.model.WishlistDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Query("SELECT :id id, EXISTS(SELECT 1 FROM wishlist WHERE id = :id) isWishlisted")
    fun isWishlistedFlow(id: Long): Flow<IsWishlistedDbModel>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist WHERE id = :id)")
    suspend fun isWishlisted(id: Long): Boolean

    @Insert
    suspend fun insert(id: WishlistDbModel)

    @Query("DELETE FROM wishlist WHERE id = :id")
    suspend fun delete(id: Long)
}
