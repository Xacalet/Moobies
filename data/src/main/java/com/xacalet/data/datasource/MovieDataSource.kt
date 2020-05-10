package com.xacalet.data.datasource

import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails

interface MovieDataSource {

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getMovieDetails(id: Long): MovieDetails
}
