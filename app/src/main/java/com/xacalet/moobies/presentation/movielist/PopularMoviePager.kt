package com.xacalet.moobies.presentation.movielist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetMoviesUseCase


class PopularMoviePager(
    private val getMoviesUseCase: GetMoviesUseCase
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> =
        try {
            val result = getMoviesUseCase(params.key ?: 1)
            LoadResult.Page(
                data = result.results,
                prevKey = (result.page - 1).takeIf { it > 0 },
                nextKey = (result.page + 1).takeIf { it <= result.totalPages }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
