package com.xacalet.moobies.di

import com.xacalet.domain.repository.GenreRepository
import com.xacalet.domain.repository.MovieRepository
import com.xacalet.domain.repository.UserRatingRepository
import com.xacalet.domain.repository.WishlistRepository
import com.xacalet.moobies.data.repository.GenreRepositoryImpl
import com.xacalet.moobies.data.repository.MovieRepositoryImpl
import com.xacalet.moobies.data.repository.UserRatingRepositoryImpl
import com.xacalet.moobies.data.repository.WishlistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Reusable
    abstract fun bindGenreRepository(genreRepository: GenreRepositoryImpl): GenreRepository

    @Binds
    @Reusable
    abstract fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Reusable
    abstract fun bindWishlistRepository(wishlistRepository: WishlistRepositoryImpl): WishlistRepository

    @Binds
    @Reusable
    abstract fun bindUserRatingRepository(userRatingRepository: UserRatingRepositoryImpl): UserRatingRepository
}
