package com.xacalet.moobies.presentation.movielist

import androidx.paging.PagingSource
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetPopularMoviesUseCase


class PopularMoviePager(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> =
        try {
            val result = getPopularMoviesUseCase(params.key ?: 1)
            LoadResult.Page(
                data = result.results,
                prevKey = (result.page - 1).takeIf { it > 0 },
                nextKey = (result.page + 1).takeIf { it <= result.totalPages }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}
