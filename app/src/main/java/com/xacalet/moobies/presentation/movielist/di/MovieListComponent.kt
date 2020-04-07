package com.xacalet.moobies.presentation.movielist.di

import com.xacalet.moobies.di.FragmentScoped
import com.xacalet.moobies.presentation.movielist.MovieListFragment
import dagger.Subcomponent

@Subcomponent
@FragmentScoped
interface MovieListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MovieListComponent
    }

    fun inject(movieListFragment: MovieListFragment)
}
