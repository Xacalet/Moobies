package com.xacalet.moobies.data.datasource

import kotlinx.coroutines.flow.Flow

interface WishlistDataSource {

    suspend fun toggleWishlist(id: Long): Boolean

    fun isWishlisted(id: Long): Flow<Boolean>
}
