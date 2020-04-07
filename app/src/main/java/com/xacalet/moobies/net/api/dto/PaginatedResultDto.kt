package com.xacalet.moobies.net.api.dto

import com.squareup.moshi.Json

/**
 * Class that represents a page of generic results returned by the Movie Database API.
 */
data class PaginatedResultDto<out T>(
    @field:Json(name = "id")
    val page: Int?,
    @field:Json(name = "results")
    val results: List<T>?,
    @field:Json(name = "total_results")
    val totalResults: Int?,
    @field:Json(name = "total_pages")
    val totalPages: Int?
)
