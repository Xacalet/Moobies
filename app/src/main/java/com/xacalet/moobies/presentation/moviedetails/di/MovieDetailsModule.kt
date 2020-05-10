package com.xacalet.moobies.presentation.moviedetails.di

import androidx.lifecycle.ViewModel
import com.xacalet.moobies.di.ViewModelKey
import com.xacalet.moobies.presentation.moviedetails.MovieDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(subcomponents = [MovieDetailsComponent::class])
abstract class MovieDetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailsViewModel): ViewModel
}
