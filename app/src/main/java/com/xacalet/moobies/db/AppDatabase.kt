package com.xacalet.moobies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xacalet.moobies.db.dao.UserRatingDao
import com.xacalet.moobies.db.dao.WishlistDao
import com.xacalet.moobies.db.model.UserRatingDbModel
import com.xacalet.moobies.db.model.WishlistDbModel

@Database(
    entities = [WishlistDbModel::class, UserRatingDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao

    abstract fun userRatingDao(): UserRatingDao
}
