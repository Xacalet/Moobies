package com.xacalet.moobies.net.api.service

import com.xacalet.moobies.net.api.dto.MovieDto
import com.xacalet.moobies.net.api.dto.PaginatedResultDto
import retrofit2.http.GET

interface MovieApiService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): PaginatedResultDto<MovieDto>
}
