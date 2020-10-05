package com.xacalet.moobies.presentation.userrating

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xacalet.domain.di.IoDispatcher
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.usecase.*
import kotlinx.coroutines.*

class UserRatingViewModel @ViewModelInject constructor(
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

    private val _otherTitlesWithSameRating = mutableStateOf<List<MovieDetails>?>(emptyList())
    val otherTitlesWithSameRating: State<List<MovieDetails>?> = _otherTitlesWithSameRating

    init {
        data = _id.switchMap { id ->
            liveData(context = viewModelScope.coroutineContext + ioDispatcher) {
                val details = getMovieDetailsUseCase(id)
                val imageUrl = details.posterPath?.let { filePath ->
                    getImageUrlUseCase(500, filePath)
                }
                val stars = getUserRatingUseCase(id)
                delay(1000) //Just to give visibility to the progress bar
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
            viewModelScope.launch {
                addUserRatingUseCase(id, stars)
            }
        }
    }

    fun onRatingRemoved() {
        _id.value?.let { id ->
            viewModelScope.launch {
                deleteUserRatingUseCase(id)
            }
        }
    }

    fun setUserRating(rating: Byte) {
        viewModelScope.launch(ioDispatcher) {
            val movies = withContext(ioDispatcher) {
                getMoviesByUserRatingUseCase(rating)
            }
            _otherTitlesWithSameRating.value = movies.filter { it.id != _id.value }
        }
    }
}
