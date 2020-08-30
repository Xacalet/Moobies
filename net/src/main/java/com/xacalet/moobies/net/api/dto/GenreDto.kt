package com.xacalet.moobies.net.api.dto

import com.squareup.moshi.Json

/**
 * Class that represents a genre returned by the Movie Database API.
 */
data class GenreDto(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "name")
    val name: String?
)
