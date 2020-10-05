package com.xacalet.moobies.di

import com.xacalet.moobies.data.datasource.GenreDataSource
import com.xacalet.moobies.data.datasource.MovieDataSource
import com.xacalet.moobies.data.datasource.UserRatingDataSource
import com.xacalet.moobies.data.datasource.WishlistDataSource
import com.xacalet.moobies.db.datasource.UserRatingDataSourceImpl
import com.xacalet.moobies.db.datasource.WishlistDataSourceImpl
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

    @Binds
    @Reusable
    abstract fun bindWishlistDataSource(wishlistDataSource: WishlistDataSourceImpl): WishlistDataSource

    @Binds
    @Reusable
    abstract fun bindUserRatingDataSource(userRatingDataSource: UserRatingDataSourceImpl): UserRatingDataSource
}
