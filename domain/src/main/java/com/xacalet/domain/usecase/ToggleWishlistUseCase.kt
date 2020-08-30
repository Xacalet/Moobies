package com.xacalet.domain.usecase

import com.xacalet.domain.repository.WishlistRepository
import javax.inject.Inject

class ToggleWishlistUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {

    suspend operator fun invoke(id: Long): Boolean = wishlistRepository.toggleWishlist(id)
}
