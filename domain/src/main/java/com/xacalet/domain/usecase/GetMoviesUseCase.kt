package com.xacalet.domain.usecase

import com.xacalet.domain.model.Movie
import com.xacalet.domain.model.PaginatedList

interface GetMoviesUseCase {

    suspend operator fun invoke(page: Int): PaginatedList<Movie>
}