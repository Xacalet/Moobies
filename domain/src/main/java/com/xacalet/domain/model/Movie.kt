package com.xacalet.domain.model

import java.io.Serializable
import java.time.LocalDate

// TODO: Implement with parcels using @Parcelize annotation or Parcelable interface.
data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val genres: List<Int>,
    val id: Long,
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
): Serializable
