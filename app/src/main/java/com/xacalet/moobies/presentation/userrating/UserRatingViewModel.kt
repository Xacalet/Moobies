package com.xacalet.moobies.presentation.userrating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Migrate to @HiltViewModel if it ever manages to work with assisted injection.
/**
 * View model that provides data to the UserRating screen.
 *
 * @param id Show id to get data from.
 * @param getMovieDetailsUseCase Use case for getting data of the provided show [id].
 * @param getImageUrlUseCase Use case for getting images.
 * @param getUserRatingUseCase Use case for getting the user rating of a show.
 * @param addUserRatingUseCase Use case for adding the user rating of a show.
 * @param deleteUserRatingUseCase Use case for removing the user rating of a show.
 * @param getMoviesByUserRatingUseCase Use case for getting shows with the same provided rating.
 * @param ioDispatcher Injected dispatcher for IO operations.
 */
class UserRatingViewModel @AssistedInject constructor(
    @Assisted val id: Long,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val getUserRatingUseCase: GetUserRatingUseCase,
    private val addUserRatingUseCase: AddUserRatingUseCase,
    private val deleteUserRatingUseCase: DeleteUserRatingUseCase,
    private val getMoviesByUserRatingUseCase: GetMoviesByUserRatingUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

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

    fun fetchOtherRatedShows(id: Long, rating: Int, posterWidth: Int) {
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

@AssistedFactory
interface UserRatingViewModelFactory {
    fun create(id: Long): UserRatingViewModel
}

/**
 * Sealed class that defines all possible states when retrieving the list of shows with the same
 * rating.
 */
sealed class GetOtherRatedShowsState {
    object Loading : GetOtherRatedShowsState()
    data class Result(val shows: List<SimpleShowListData>) : GetOtherRatedShowsState()
}
