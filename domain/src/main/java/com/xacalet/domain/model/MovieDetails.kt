package com.xacalet.domain.model

import java.time.LocalDate

class MovieDetails(
    val id: Long,
    val backdropPath: String?,
    val posterPath: String?,
    val genres: List<Genre>,
    val originalTitle: String?,
    val overview: String?,
    val releaseDate: LocalDate?,
    val runtime: Int,
    val title: String?,
    val voteAverage: Double,
    val voteCount: Int
)
