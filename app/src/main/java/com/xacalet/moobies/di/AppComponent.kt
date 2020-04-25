package com.xacalet.moobies.di

import com.xacalet.moobies.MainActivity
import com.xacalet.moobies.net.di.NetworkModule
import com.xacalet.moobies.presentation.movielist.di.MovieListComponent
import com.xacalet.moobies.presentation.movielist.di.MovieListModule
import dagger.Component
import javax.inject.Singleton

/**
 * Main Dagger component for the application.
 */
@Component(
    modules = [
        DataSourceModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        MovieListModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

    fun loginComponent(): MovieListComponent.Factory
}
