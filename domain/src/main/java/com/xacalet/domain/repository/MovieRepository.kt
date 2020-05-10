package com.xacalet.domain.repository

import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails

interface MovieRepository {

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getMovieDetails(id: Long): MovieDetails
}
