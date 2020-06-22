package com.xacalet.domain.model

data class PaginatedList<T>(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val results: List<T>
)
