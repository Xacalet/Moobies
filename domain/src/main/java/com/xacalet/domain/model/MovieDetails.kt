package com.xacalet.domain.model

import java.time.LocalDate

class MovieDetails(
    val id: Int?,
    val backdropPath: String?,
    val posterPath: String?,
    val genres: List<Genre>,
    val originalTitle: String?,
    val overview: String?,
    val releaseDate: LocalDate?,
    val runtime: Int
)
