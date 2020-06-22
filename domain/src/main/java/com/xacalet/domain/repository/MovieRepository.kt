package com.xacalet.domain.repository

import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.model.PaginatedList

interface MovieRepository {

    suspend fun getPopularMovies(page: Int): PaginatedList<Movie>

    suspend fun getMovieDetails(id: Long): MovieDetails
}
