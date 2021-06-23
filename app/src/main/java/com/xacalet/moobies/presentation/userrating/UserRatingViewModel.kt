package com.xacalet.moobies.presentation.userrating

import androidx.lifecycle.*
import com.xacalet.domain.di.IoDispatcher
import com.xacalet.domain.usecase.*
import com.xacalet.moobies.presentation.components.SimpleShowListData
import com.xacalet.utils.android.lifecycle.MutableSingleLiveEvent
import com.xacalet.utils.android.lifecycle.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model that provides data to the UserRating screen.
 *
 * @param getMovieDetailsUseCase Use case for getting data of the provided show [id].
 * @param getImageUrlUseCase Use case for getting images.
 * @param getUserRatingUseCase Use case for getting the user rating of a show.
 * @param addUserRatingUseCase Use case for adding the user rating of a show.
 * @param deleteUserRatingUseCase Use case for removing the user rating of a show.
 * @param getMoviesByUserRatingUseCase Use case for getting shows with the same provided rating.
 * @param savedStateHandle
 * @param ioDispatcher Injected dispatcher for IO operations.
 */
@HiltViewModel
class UserRatingViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val getUserRatingUseCase: GetUserRatingUseCase,
    private val addUserRatingUseCase: AddUserRatingUseCase,
    private val deleteUserRatingUseCase: DeleteUserRatingUseCase,
    private val getMoviesByUserRatingUseCase: GetMoviesByUserRatingUseCase,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val id: Long = savedStateHandle.get<Long>("showId") ?: -1L

    val data: LiveData<UserRatingUiModel>

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
        data = liveData(context = viewModelScope.coroutineContext + ioDispatcher) {
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

    fun onRatingChanged(stars: Int) {
        viewModelScope.launch(ioDispatcher) {
            _isLoading.postValue(true)
            addUserRatingUseCase(id, stars)
            _isLoading.postValue(false)
            _onRatingChanged.postValue(Unit)
        }
    }

    fun onRatingRemoved() {
        viewModelScope.launch(ioDispatcher) {
            _isLoading.postValue(true)
            deleteUserRatingUseCase(id)
            _isLoading.postValue(false)
            _onRatingRemoved.postValue(Unit)
        }
    }

    fun fetchOtherRatedShows(rating: Int, posterWidth: Int) {
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

/**
 * Sealed class that defines all possible states when retrieving the list of shows with the same
 * rating.
 */
sealed class GetOtherRatedShowsState {
    object Loading : GetOtherRatedShowsState()
    data class Result(val shows: List<SimpleShowListData>) : GetOtherRatedShowsState()
}
