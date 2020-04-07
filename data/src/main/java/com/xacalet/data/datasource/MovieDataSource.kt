package com.xacalet.data.datasource

import com.xacalet.domain.model.Movie

interface MovieDataSource {
    suspend fun getPopularMovies(): List<Movie>
}
