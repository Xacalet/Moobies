package com.xacalet.moobies.net.api.service

import com.xacalet.moobies.net.api.dto.GenresDto
import retrofit2.http.GET

interface GenreApiService {

    @GET("/3/genre/movie/list")
    suspend fun getGenres(): GenresDto
}
