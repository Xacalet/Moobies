package com.xacalet.moobies.di

import com.xacalet.data.datasource.ConfigurationDataSource
import com.xacalet.data.datasource.GenreDataSource
import com.xacalet.data.datasource.MovieDataSource
import com.xacalet.moobies.net.datasource.ConfigurationDataSourceImpl
import com.xacalet.moobies.net.datasource.GenreDataSourceImpl
import com.xacalet.moobies.net.datasource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Suppress("unused")
@Module
abstract class DataSourceModule {

    @Binds
    @Reusable
    abstract fun bindGenreDataSource(genreDataSource: GenreDataSourceImpl): GenreDataSource

    @Binds
    @Reusable
    abstract fun bindMovieDataSource(movieDataSource: MovieDataSourceImpl): MovieDataSource

    @Binds
    @Reusable
    abstract fun bindConfigurationDataSource(configurationDataSource: ConfigurationDataSourceImpl): ConfigurationDataSource
}
