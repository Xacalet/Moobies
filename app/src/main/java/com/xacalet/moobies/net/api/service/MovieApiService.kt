package com.xacalet.moobies.net.api.service

import com.xacalet.moobies.net.api.dto.MovieDetailsDto
import com.xacalet.moobies.net.api.dto.MovieDto
import com.xacalet.moobies.net.api.dto.PaginatedResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(@Query(PAGE_PARAM) page: Int): PaginatedResultDto<MovieDto>

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query(PAGE_PARAM) page: Int): PaginatedResultDto<MovieDto>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(@Query(PAGE_PARAM) page: Int): PaginatedResultDto<MovieDto>

    @GET("/3/movie/{$MOVIE_DETAILS_ID}")
    suspend fun getMovieDetails(@Path(MOVIE_DETAILS_ID) id: Long): MovieDetailsDto

    private companion object {
        const val MOVIE_DETAILS_ID = "id"
        const val PAGE_PARAM = "page"
    }
}
