package com.xacalet.domain.usecase

import com.xacalet.domain.repository.UserRatingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserRatingUseCase @Inject constructor(
    private val userRatingRepository: UserRatingRepository
) {

    suspend operator fun invoke(id: Long): Int? =
        withContext(Dispatchers.IO) {
            userRatingRepository.getUserRating(id)
        }
}
