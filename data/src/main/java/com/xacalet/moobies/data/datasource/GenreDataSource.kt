package com.xacalet.moobies.data.datasource

import com.xacalet.domain.model.Genre

interface GenreDataSource {

    suspend fun getGenres(): List<Genre>
}
