package com.xacalet.data.datasource

import kotlinx.coroutines.flow.Flow

interface WishlistDataSource {

    suspend fun toggleWishlist(id: Long): Boolean

    fun isWishlisted(id: Long): Flow<Boolean>
}
