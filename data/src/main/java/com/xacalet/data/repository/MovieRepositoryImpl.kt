package com.xacalet.data.repository

import com.xacalet.data.datasource.GenreDataSource
import com.xacalet.data.datasource.MovieDataSource
import com.xacalet.domain.model.Genre
import com.xacalet.domain.model.Movie
import com.xacalet.domain.repository.GenreRepository
import com.xacalet.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource
): MovieRepository {
    override suspend fun getPopularMovies(): List<Movie> = dataSource.getPopularMovies()
}
