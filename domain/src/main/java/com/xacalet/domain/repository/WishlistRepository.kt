package com.xacalet.domain.repository

import kotlinx.coroutines.flow.Flow

interface WishlistRepository {

    suspend fun toggleWishlist(id: Long): Boolean

    fun isWishlisted(id: Long): Flow<Boolean>
}
