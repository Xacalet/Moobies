package com.xacalet.moobies.presentation.userrating

import androidx.lifecycle.*
import com.xacalet.domain.di.IoDispatcher
import com.xacalet.domain.usecase.*
import com.xacalet.moobies.presentation.components.SimpleShowListData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRatingViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val getUserRatingUseCase: GetUserRatingUseCase,
    private val addUserRatingUseCase: AddUserRatingUseCase,
    private val deleteUserRatingUseCase: DeleteUserRatingUseCase,
    private val getMoviesByUserRatingUseCase: GetMoviesByUserRatingUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _id = MutableLiveData<Long>()
    var data: LiveData<UserRatingUiModel>
        private set

    private val _otherRatedShows =
        MutableLiveData<GetOtherRatedShowsState>(GetOtherRatedShowsState.Loading)
    val otherRatedShows: LiveData<GetOtherRatedShowsState> = _otherRatedShows

    init {
        data = _id.switchMap { id ->
            liveData(context = viewModelScope.coroutineContext + ioDispatcher) {
                val details = getMovieDetailsUseCase(id)
                val imageUrl = details.posterPath?.let { filePath ->
                    getImageUrlUseCase(500, filePath)
                }
                val stars = getUserRatingUseCase(id)
                emit(UserRatingUiModel(id, details.title ?: "", stars, imageUrl))
            }
        }
    }

    fun setId(id: Long) {
        if (_id.value != id) {
            _id.value = id
        }
    }

    fun onRatingChanged(stars: Byte) {
        _id.value?.let { id ->
            viewModelScope.launch(ioDispatcher) {
                addUserRatingUseCase(id, stars)
            }
        }
    }

    fun onRatingRemoved() {
        _id.value?.let { id ->
            viewModelScope.launch(ioDispatcher) {
                deleteUserRatingUseCase(id)
            }
        }
    }

    fun fetchOtherRatedShows(id: Long, rating: Byte, posterWidth: Int) {
        // TODO: This seems to be executing more times than expected.
        viewModelScope.launch(ioDispatcher) {
            _otherRatedShows.postValue(GetOtherRatedShowsState.Loading)
            val result = getMoviesByUserRatingUseCase(rating)
                .filter { it.id != id }
                .map { show ->
                    val imageUrl = getImageUrlUseCase(posterWidth, show.posterPath ?: "")
                    SimpleShowListData(
                        id = show.id,
                        imageUrl = imageUrl,
                        title = show.title ?: "",
                        year = show.releaseDate?.year?.toString() ?: ""
                    )
                }
            delay(1000) //Just to give visibility to the progress bar
            _otherRatedShows.postValue(GetOtherRatedShowsState.Result(result))
        }
    }
}

sealed class GetOtherRatedShowsState {
    object Loading : GetOtherRatedShowsState()
    data class Result(val shows: List<SimpleShowListData>) : GetOtherRatedShowsState()
}
