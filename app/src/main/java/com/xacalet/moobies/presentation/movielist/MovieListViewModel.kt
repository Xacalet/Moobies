package com.xacalet.moobies.presentation.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.xacalet.utils.android.lifecycle.MutableSingleLiveEvent
import com.xacalet.utils.android.lifecycle.SingleLiveEvent
import com.xacalet.domain.model.Movie
import com.xacalet.domain.usecase.GetUpcomingMoviesUseCase
import com.xacalet.domain.usecase.ToggleWishlistUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MovieListViewModel @ViewModelInject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase
) : ViewModel() {

    private val _addedToWishList = MutableSingleLiveEvent<Boolean>()

    val pagingFlow: Flow<PagingData<Movie>> = Pager(PagingConfig(20)) {
        PopularMoviePager(getUpcomingMoviesUseCase)
    }.flow.cachedIn(viewModelScope)

    val addedToWishlist: SingleLiveEvent<Boolean> = _addedToWishList

    fun toggleWishlist(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val isWishlisted = toggleWishlistUseCase(id)
            _addedToWishList.postValue(isWishlisted)
        }
    }
}
