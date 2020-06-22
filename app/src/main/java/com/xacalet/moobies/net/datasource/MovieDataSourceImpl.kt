package com.xacalet.moobies.net.datasource

import com.xacalet.data.datasource.MovieDataSource
import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.model.PaginatedList
import com.xacalet.moobies.net.api.service.MovieApiService
import com.xacalet.moobies.net.mapper.toEntity
import com.xacalet.moobies.net.mapper.toEntityList
import retrofit2.Retrofit
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val client: Retrofit
) : MovieDataSource {

    override suspend fun getPopularMovies(page: Int): PaginatedList<Movie> =
        client.create(MovieApiService::class.java).run {
            getPopularMovies(page).toEntityList { it.toEntity() }
        }

    override suspend fun getMovieDetails(id: Long): MovieDetails =
        client.create(MovieApiService::class.java).run {
            getMovieDetails(id).toEntity()
        }
}
