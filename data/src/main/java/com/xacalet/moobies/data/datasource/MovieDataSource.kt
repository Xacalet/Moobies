package com.xacalet.moobies.data.datasource

import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.model.PaginatedList

interface MovieDataSource {

    suspend fun getPopularMovies(page: Int): PaginatedList<Movie>

    suspend fun getUpcomingMovies(page: Int): PaginatedList<Movie>

    suspend fun getNowPlayingMovies(page: Int): PaginatedList<Movie>

    suspend fun getMovieDetails(id: Long): MovieDetails
}
