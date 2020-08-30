package com.xacalet.moobies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xacalet.moobies.db.dao.WishlistDao
import com.xacalet.moobies.db.model.WishlistDbModel

@Database(entities = [WishlistDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}
