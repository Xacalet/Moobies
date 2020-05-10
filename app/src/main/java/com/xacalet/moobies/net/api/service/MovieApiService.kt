package com.xacalet.moobies.net.api.service

import com.xacalet.moobies.net.api.dto.MovieDetailsDto
import com.xacalet.moobies.net.api.dto.MovieDto
import com.xacalet.moobies.net.api.dto.PaginatedResultDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): PaginatedResultDto<MovieDto>

    @GET("/3/movie/{$MOVIE_DETAILS_ID}")
    suspend fun getMovieDetails(@Path(MOVIE_DETAILS_ID) id: Long): MovieDetailsDto

    private companion object {
        const val MOVIE_DETAILS_ID = "id"
    }
}
