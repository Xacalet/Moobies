package com.xacalet.domain.repository

import com.xacalet.domain.model.Genre
import com.xacalet.domain.model.Movie

interface MovieRepository {

    suspend fun getPopularMovies(): List<Movie>
}
