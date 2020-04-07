package com.xacalet.moobies.presentation.movielist.di

import com.xacalet.domain.usecase.GetPopularMoviesUseCase
import com.xacalet.moobies.presentation.movielist.MovieListViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module(subcomponents = [MovieListComponent::class])
class MovieListModule {

    @Provides
    @Reusable
    fun getMovieListViewModelFactory(getPopularMoviesUseCase: GetPopularMoviesUseCase): MovieListViewModelFactory =
        MovieListViewModelFactory(getPopularMoviesUseCase)
}
