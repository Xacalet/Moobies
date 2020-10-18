package com.xacalet.moobies.data.repository

import com.xacalet.domain.model.Genre
import com.xacalet.domain.repository.GenreRepository
import com.xacalet.moobies.data.datasource.GenreDataSource
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val dataSource: GenreDataSource
) : GenreRepository {

    override suspend fun getGenres(): List<Genre> = dataSource.getGenres()
}
