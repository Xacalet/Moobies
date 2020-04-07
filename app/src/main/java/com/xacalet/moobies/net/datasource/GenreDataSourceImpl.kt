package com.xacalet.moobies.net.datasource

import com.xacalet.data.datasource.GenreDataSource
import com.xacalet.domain.model.Genre
import com.xacalet.moobies.net.api.service.GenreApiService
import com.xacalet.moobies.net.mapper.toEntity
import retrofit2.Retrofit
import javax.inject.Inject

class GenreDataSourceImpl @Inject constructor(
    private val client: Retrofit
): GenreDataSource {

    override suspend fun getGenres(): List<Genre> =
        client.create(GenreApiService::class.java).run {
            getGenres().toEntity()
        }
}
