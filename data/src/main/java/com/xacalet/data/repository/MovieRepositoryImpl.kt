package com.xacalet.data.repository

import com.xacalet.data.datasource.MovieDataSource
import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.model.PaginatedList
import com.xacalet.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): PaginatedList<Movie> =
        dataSource.getPopularMovies(page)

    override suspend fun getUpcomingMovies(page: Int): PaginatedList<Movie> =
        dataSource.getUpcomingMovies(page)

    override suspend fun getNowPlayingMovies(page: Int): PaginatedList<Movie> =
        dataSource.getNowPlayingMovies(page)

    override suspend fun getMovieDetails(id: Long): MovieDetails = dataSource.getMovieDetails(id)
}
