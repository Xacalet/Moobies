package com.xacalet.domain.model

import java.time.LocalDate

data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val genres: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: LocalDate,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
