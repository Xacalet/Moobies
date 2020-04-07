package com.xacalet.moobies.net.mapper

import com.xacalet.domain.model.Genre
import com.xacalet.domain.model.Movie
import com.xacalet.moobies.net.api.dto.GenreDto
import com.xacalet.moobies.net.api.dto.GenresDto
import com.xacalet.moobies.net.api.dto.MovieDto
import com.xacalet.moobies.net.api.dto.PaginatedResultDto
import java.time.LocalDate
import java.time.LocalDate.parse
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE


// TODO: Transform this into mapper interfaces + impl.
/**
 * Maps this [MovieDto] instance into a [Movie] class instance.
 */
fun MovieDto.toEntity() = Movie(
    adult = this.adult ?: false,
    backdropPath = this.backdropPath,
    genres = this.genreIds?.filterNotNull() ?: emptyList(),
    id = this.id ?: -1,
    originalLanguage = this.originalLanguage ?: "",
    originalTitle = this.originalTitle ?: "",
    overview = this.overview ?: "",
    popularity = this.popularity ?: .0,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate.toLocalDate(),
    title = this.title ?: "",
    video = this.video ?: false,
    voteAverage = this.voteAverage ?: .0,
    voteCount = this.voteCount ?: 0
)

/**
 * Maps this [GenreDto] instance into a [Genre] class instance.
 */
fun GenreDto.toEntity() = Genre(
    id = this.id ?: -1,
    name = this.name ?: ""
)

/**
 * Maps this [GenresDto] instance into a list of [Genre].
 */
fun GenresDto?.toEntity(): List<Genre> = this?.genres?.map(
    GenreDto::toEntity
) ?: emptyList()

/**
 * Maps results from [PaginatedResultDto] into a list of the same generic type.
 *
 * @param entityMapper Function that will map a single instance of type T into type S.
 */
fun <T, S> PaginatedResultDto<T?>?.toEntityList(entityMapper: (T?) -> S): List<S> =
    this?.results?.map(entityMapper) ?: emptyList()

/**
 * Maps this string date into a [LocalDate] instance.
 *
 * @param dateFormatter [DateTimeFormatter] with the logic to parse the string into a date.
 * @param defaultDate [LocalDate] instance to be used if the string is null.
 */
fun String?.toLocalDate(
    dateFormatter: DateTimeFormatter = ISO_LOCAL_DATE,
    defaultDate: LocalDate = LocalDate.now()
): LocalDate = this?.let { date -> parse(date, dateFormatter) } ?: defaultDate


