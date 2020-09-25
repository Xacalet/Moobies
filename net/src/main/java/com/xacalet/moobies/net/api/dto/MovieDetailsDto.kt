package com.xacalet.moobies.net.api.dto

import com.squareup.moshi.Json


/**
 * Class that represents a movie returned by the Movie Database API.
 */
data class MovieDetailsDto(
    @field:Json(name = "adult")
    val adult: Boolean? = null,
    @field:Json(name = "backdrop_path")
    val backdropPath: String? = null,
    @field:Json(name = "belongs_to_collection")
    val belongsToCollection: Any? = null,
    @field:Json(name = "budget")
    val budget: Int? = null,
    @field:Json(name = "genres")
    val genres: List<GenreDto?>? = null,
    @field:Json(name = "homepage")
    val homepage: String? = null,
    @field:Json(name = "id")
    val id: Long? = null,
    @field:Json(name = "imdb_id")
    val imdbId: String? = null,
    @field:Json(name = "original_language")
    val originalLanguage: String? = null,
    @field:Json(name = "original_title")
    val originalTitle: String? = null,
    @field:Json(name = "overview")
    val overview: String? = null,
    @field:Json(name = "popularity")
    val popularity: Double? = null,
    @field:Json(name = "poster_path")
    val posterPath: String? = null,
    @field:Json(name = "production_companies")
    val productionCompanies: List<ProductionCompanyDto?>? = null,
    @field:Json(name = "production_countries")
    val productionCountries: List<ProductionCountryDto?>? = null,
    @field:Json(name = "release_date")
    val releaseDate: String? = null,
    @field:Json(name = "revenue")
    val revenue: Int? = null,
    @field:Json(name = "runtime")
    val runtime: Int? = null,
    @field:Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto?>? = null,
    @field:Json(name = "status")
    val status: String? = null,
    @field:Json(name = "tagline")
    val tagline: String? = null,
    @field:Json(name = "title")
    val title: String? = null,
    @field:Json(name = "video")
    val video: Boolean? = null,
    @field:Json(name = "vote_average")
    val voteAverage: Double? = null,
    @field:Json(name = "vote_count")
    val voteCount: Int? = null
)


