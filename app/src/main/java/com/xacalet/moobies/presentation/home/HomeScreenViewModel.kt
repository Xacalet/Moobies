package com.xacalet.moobies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.GetPopularMoviesUseCase
import com.xacalet.domain.usecase.GetUpcomingMoviesUseCase
import com.xacalet.domain.usecase.IsWishlistedFlowUseCase
import com.xacalet.domain.usecase.ToggleWishlistUseCase
import com.xacalet.moobies.presentation.movielist.PopularMoviePager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val isWishlistedFlowUseCase: IsWishlistedFlowUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
) : ViewModel() {

    private val _popularMoviesState =
        MutableStateFlow<PopularShowsDataState>(PopularShowsDataState.Loading)
    val popularShowsDataState: StateFlow<PopularShowsDataState> = _popularMoviesState

    init {
        viewModelScope.launch {
                _popularMoviesState.value = try {
                // TODO: Move this to the user case?
                delay(5000)
                getPopularMoviesUseCase().filter { it.id != -1L }.map { show ->
                    HeaderItemData(
                        id = show.id,
                        title = show.title,
                        subtitle = "Subtitle", //TODO: Compose subtitle
                        posterUrl = getImageUrlUseCase(
                            200,
                            show.posterPath ?: ""
                        ), //TODO: Get actual metrics
                        backdropUrl = getImageUrlUseCase(
                            1000,
                            show.backdropPath ?: ""
                        ), //TODO: Get actual metrics
                        isWishlisted = isWishlistedFlowUseCase(show.id).stateIn(viewModelScope),
                    )
                }.let {
                    PopularShowsDataState.Ready(it)
                }
            } catch (e: Exception) {
                PopularShowsDataState.Error(e.message)
            }
        }
    }

    val upcomingMoviesFlow: Flow<PagingData<ShowItemData>> = Pager(PagingConfig(20)) {
        PopularMoviePager(getUpcomingMoviesUseCase)
    }.flow.map { pagingData ->
        pagingData.map { show ->
            ShowItemData(
                id = show.id,
                title = show.title,
                subtitle = "${show.releaseDate.year}",
                score = show.voteAverage,
                posterUrl = getImageUrlUseCase(
                    200,
                    show.posterPath ?: ""
                ), //TODO: Get actual metrics
                isWishlisted = isWishlistedFlowUseCase(show.id).stateIn(viewModelScope)
            )
        }
    }.cachedIn(viewModelScope)

    @ExperimentalCoroutinesApi
    fun toggleWishlist(id: Long) {
        viewModelScope.launch {
            toggleWishlistUseCase(id)
        }
    }
}

sealed class PopularShowsDataState {
    object Loading : PopularShowsDataState()
    class Error(val message: String?) : PopularShowsDataState()
    class Ready(val data: List<HeaderItemData>) : PopularShowsDataState()
}

data class HeaderItemData(
    val id: Long,
    val title: String?,
    val subtitle: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val isWishlisted: StateFlow<Boolean>
)
