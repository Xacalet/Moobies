package com.xacalet.moobies.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class WishlistDbModel(
    @PrimaryKey val id: Long
)
