package com.xacalet.moobies.db.datasource

import com.xacalet.moobies.data.datasource.WishlistDataSource
import com.xacalet.moobies.db.dao.WishlistDao
import com.xacalet.moobies.db.model.WishlistDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class WishlistDataSourceImpl @Inject constructor(
    private val wishlistDao: WishlistDao
) : WishlistDataSource {

    override suspend fun toggleWishlist(id: Long): Boolean {
        return if (wishlistDao.isWishlisted(id)) {
            wishlistDao.delete(id)
            false
        } else {
            wishlistDao.insert(WishlistDbModel(id))
            true
        }
    }

    override fun isWishlisted(id: Long): Flow<Boolean> =
        wishlistDao.isWishlistedFlow(id).distinctUntilChanged().transform { emit(it.isWishlisted) }
}
