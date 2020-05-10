package com.xacalet.moobies.di

import com.xacalet.data.repository.GenreRepositoryImpl
import com.xacalet.data.repository.MovieRepositoryImpl
import com.xacalet.domain.repository.GenreRepository
import com.xacalet.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Suppress("unused")
@Module
abstract class RepositoryModule {

    @Binds
    @Reusable
    abstract fun bindGenreRepository(genreRepository: GenreRepositoryImpl): GenreRepository

    @Binds
    @Reusable
    abstract fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}
