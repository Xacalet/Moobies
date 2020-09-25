package com.xacalet.moobies.di

import android.content.Context
import androidx.room.Room
import com.xacalet.moobies.db.AppDatabase
import com.xacalet.moobies.db.dao.UserRatingDao
import com.xacalet.moobies.db.dao.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

    @Provides
    @Singleton
    fun provideWishlistDao(appDatabase: AppDatabase): WishlistDao = appDatabase.wishlistDao()

    @Provides
    @Singleton
    fun provideUserRatingDao(appDatabase: AppDatabase): UserRatingDao = appDatabase.userRatingDao()
}
