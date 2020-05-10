package com.xacalet.moobies.presentation.moviedetails.di

import com.xacalet.moobies.di.FragmentScoped
import com.xacalet.moobies.presentation.moviedetails.MovieDetailsFragment
import dagger.Subcomponent

@Subcomponent
@FragmentScoped
interface MovieDetailsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieDetailsComponent
    }

    fun inject(movieDetailsFragment: MovieDetailsFragment)
}
