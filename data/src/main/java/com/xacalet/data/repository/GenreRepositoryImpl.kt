package com.xacalet.data.repository

import com.xacalet.data.datasource.GenreDataSource
import com.xacalet.domain.model.Genre
import com.xacalet.domain.repository.GenreRepository
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val dataSource: GenreDataSource
): GenreRepository {
    override suspend fun getGenres(): List<Genre> = dataSource.getGenres()
}
