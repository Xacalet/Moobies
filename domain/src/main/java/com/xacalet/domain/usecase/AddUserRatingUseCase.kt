package com.xacalet.domain.usecase

import com.xacalet.domain.repository.UserRatingRepository
import com.xacalet.domain.repository.WishlistRepository
import javax.inject.Inject

class AddUserRatingUseCase @Inject constructor(
    private val userRatingRepository: UserRatingRepository
) {

    suspend operator fun invoke(id: Long, stars: Int) =
        userRatingRepository.addUserRating(id, stars)
}
