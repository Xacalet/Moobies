package com.xacalet.data.datasource

import com.xacalet.domain.model.Genre

interface GenreDataSource {
    suspend fun getGenres(): List<Genre>
}
