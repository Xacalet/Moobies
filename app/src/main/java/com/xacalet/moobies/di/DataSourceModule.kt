package com.xacalet.moobies.di

import com.xacalet.data.datasource.GenreDataSource
import com.xacalet.data.datasource.MovieDataSource
import com.xacalet.moobies.net.datasource.GenreDataSourceImpl
import com.xacalet.moobies.net.datasource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    @Reusable
    abstract fun bindGenreDataSource(genreDataSource: GenreDataSourceImpl): GenreDataSource

    @Binds
    @Reusable
    abstract fun bindMovieDataSource(movieDataSource: MovieDataSourceImpl): MovieDataSource
}
