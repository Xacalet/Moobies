package com.xacalet.domain.repository

import com.xacalet.domain.model.Genre
import javax.inject.Singleton

@Singleton
interface GenreRepository {

    suspend fun getGenres(): List<Genre>
}
