package com.xacalet.domain.usecase

import com.xacalet.domain.repository.UserRatingRepository
import javax.inject.Inject

class DeleteUserRatingUseCase @Inject constructor(
    private val userRatingRepository: UserRatingRepository
) {

    suspend operator fun invoke(id: Long) =
        userRatingRepository.deleteUserRating(id)
}
