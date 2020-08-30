package com.xacalet.data.repository

import com.xacalet.data.datasource.WishlistDataSource
import com.xacalet.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    private val wishlistDataSource: WishlistDataSource
): WishlistRepository {

    override suspend fun toggleWishlist(id: Long): Boolean = wishlistDataSource.toggleWishlist(id)

    override fun isWishlisted(id: Long): Flow<Boolean> = wishlistDataSource.isWishlisted(id)
}
