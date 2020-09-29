package com.xacalet.moobies.presentation.userrating

data class UserRatingUiModel(
    val showId: Long,
    val showName: String,
    val stars: Byte?,
    val poserImageUrl: String?
)
