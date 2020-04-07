package com.xacalet.moobies.net.api.dto

import com.squareup.moshi.Json

/**
 * Class that represents a list of genres returned by the Movie Database API.
 */
data class GenresDto(
    @field:Json(name = "genres")
    val genres: List<GenreDto>
)
