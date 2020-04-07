package com.xacalet.domain.usecase

import com.xacalet.domain.model.Genre
import com.xacalet.domain.repository.GenreRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: GenreRepository
) {
    suspend fun execute(): List<Genre> = repository.getGenres()
}
