package com.xacalet.domain.usecase

import com.xacalet.domain.repository.WishlistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsWishlistedFlowUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {

    operator fun invoke(id: Long): Flow<Boolean> = wishlistRepository.isWishlisted(id)
}
