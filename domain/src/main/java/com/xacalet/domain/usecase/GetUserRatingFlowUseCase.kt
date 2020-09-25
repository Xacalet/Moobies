package com.xacalet.domain.usecase

import com.xacalet.domain.repository.UserRatingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRatingFlowUseCase @Inject constructor(
    private val userRatingRepository: UserRatingRepository
) {

    operator fun invoke(id: Long): Flow<Byte?> = userRatingRepository.getUserRatingFlow(id)
}
