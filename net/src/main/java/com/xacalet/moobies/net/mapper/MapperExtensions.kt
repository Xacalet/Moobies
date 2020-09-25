package com.xacalet.moobies.net.mapper

import com.xacalet.domain.model.Genre
import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.model.PaginatedList
import com.xacalet.moobies.net.api.dto.*
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
    id = this.id?.toLong() ?: -1L,
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
fun <T: Any, S> PaginatedResultDto<T?>?.toEntityList(entityMapper: (T) -> S): PaginatedList<S> =
    PaginatedList(
        this?.page ?: 0,
        this?.totalResults ?: 0,
        this?.totalPages ?: 0,
        this?.results?.filterNotNull()?.map(entityMapper) ?: emptyList()
    )

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

// TODO: Move mapping
fun MovieDetailsDto?.toEntity(): MovieDetails =
    MovieDetails(
        id = this?.id ?: -1L,
        backdropPath = this?.backdropPath,
        posterPath = this?.posterPath,
        genres = this?.genres?.mapNotNull { it?.toEntity() } ?: emptyList(),
        originalTitle = this?.originalTitle,
        overview = this?.overview,
        releaseDate = this?.releaseDate?.toLocalDate(),
        runtime = this?.runtime ?: 0,
        title = this?.title,
        voteAverage = this?.voteAverage ?: .0,
        voteCount = this?.voteCount ?: 0
    )
