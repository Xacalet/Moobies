package com.xacalet.moobies.presentation.movielist.di

import androidx.lifecycle.ViewModel
import com.xacalet.moobies.di.ViewModelKey
import com.xacalet.moobies.presentation.movielist.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = [MovieListComponent::class])
abstract class MovieListModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun bindMovieListViewModel(viewModel: MovieListViewModel): ViewModel
}
