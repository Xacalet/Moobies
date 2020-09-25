package com.xacalet.moobies.di

import com.xacalet.data.repository.GenreRepositoryImpl
import com.xacalet.data.repository.MovieRepositoryImpl
import com.xacalet.data.repository.UserRatingRepositoryImpl
import com.xacalet.data.repository.WishlistRepositoryImpl
import com.xacalet.domain.repository.GenreRepository
import com.xacalet.domain.repository.MovieRepository
import com.xacalet.domain.repository.UserRatingRepository
import com.xacalet.domain.repository.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
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
