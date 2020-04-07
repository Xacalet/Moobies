package com.xacalet.moobies.di

import com.xacalet.data.datasource.GenreDataSource
import com.xacalet.data.datasource.MovieDataSource
import com.xacalet.data.repository.GenreRepositoryImpl
import com.xacalet.data.repository.MovieRepositoryImpl
import com.xacalet.domain.repository.GenreRepository
import com.xacalet.domain.repository.MovieRepository
import com.xacalet.moobies.net.datasource.GenreDataSourceImpl
import com.xacalet.moobies.net.datasource.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Suppress("unused")
@Module
abstract class GeneralModule {

    @Binds
    @Reusable
    abstract fun bindGenreRepository(genreRepository: GenreRepositoryImpl): GenreRepository

    @Binds
    @Reusable
    abstract fun bindGenreDataSource(genreDataSource: GenreDataSourceImpl): GenreDataSource

    @Binds
    @Reusable
    abstract fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Reusable
    abstract fun bindMovieDataSource(movieDataSource: MovieDataSourceImpl): MovieDataSource
}
