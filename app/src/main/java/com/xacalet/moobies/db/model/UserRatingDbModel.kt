package com.xacalet.moobies.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_rating")
data class UserRatingDbModel(
    @PrimaryKey val id: Long,
    val stars: Int
)
