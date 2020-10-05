package com.xacalet.moobies.data.repository

import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.model.PaginatedList
import com.xacalet.domain.repository.MovieRepository
import com.xacalet.moobies.data.datasource.MovieDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): PaginatedList<Movie> =
        movieDataSource.getPopularMovies(page)

    override suspend fun getUpcomingMovies(page: Int): PaginatedList<Movie> =
        movieDataSource.getUpcomingMovies(page)

    override suspend fun getNowPlayingMovies(page: Int): PaginatedList<Movie> =
        movieDataSource.getNowPlayingMovies(page)

    override suspend fun getMovieDetails(id: Long): MovieDetails =
        movieDataSource.getMovieDetails(id)
}
