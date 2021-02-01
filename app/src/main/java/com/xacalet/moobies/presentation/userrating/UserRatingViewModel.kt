package com.xacalet.moobies.presentation.userrating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.xacalet.domain.di.IoDispatcher
import com.xacalet.domain.usecase.AddUserRatingUseCase
import com.xacalet.domain.usecase.DeleteUserRatingUseCase
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.GetMovieDetailsUseCase
import com.xacalet.domain.usecase.GetMoviesByUserRatingUseCase
import com.xacalet.domain.usecase.GetUserRatingUseCase
import com.xacalet.moobies.presentation.components.SimpleShowListData
import com.xacalet.utils.android.lifecycle.MutableSingleLiveEvent
import com.xacalet.utils.android.lifecycle.SingleLiveEvent
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

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _otherRatedShows =
        MutableLiveData<GetOtherRatedShowsState>(GetOtherRatedShowsState.Loading)
    val otherRatedShows: LiveData<GetOtherRatedShowsState> = _otherRatedShows

    private val _onRatingRemoved = MutableSingleLiveEvent<Unit>()
    val onRatingRemoved: SingleLiveEvent<Unit> = _onRatingRemoved

    private val _onRatingChanged = MutableSingleLiveEvent<Unit>()
    val onRatingChanged: SingleLiveEvent<Unit> = _onRatingChanged

    init {
        data = _id.switchMap { id ->
            liveData(context = viewModelScope.coroutineContext + ioDispatcher) {
                _isLoading.postValue(true)
                val details = getMovieDetailsUseCase(id)
                // TODO: Get image width from screen width
                val imageUrl = details.posterPath?.let { filePath ->
                    getImageUrlUseCase(500, filePath)
                }
                val stars = getUserRatingUseCase(id)
                emit(UserRatingUiModel(id, details.title ?: "", stars, imageUrl))
                _isLoading.postValue(false)
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
                _isLoading.postValue(true)
                addUserRatingUseCase(id, stars)
                _isLoading.postValue(false)
                _onRatingChanged.postValue(Unit)
            }
        }
    }

    fun onRatingRemoved() {
        _id.value?.let { id ->
            viewModelScope.launch(ioDispatcher) {
                _isLoading.postValue(true)
                deleteUserRatingUseCase(id)
                _isLoading.postValue(false)
                _onRatingRemoved.postValue(Unit)
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
