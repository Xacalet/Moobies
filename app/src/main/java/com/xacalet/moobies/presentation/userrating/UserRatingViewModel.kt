package com.xacalet.moobies.presentation.userrating

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xacalet.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserRatingViewModel @ViewModelInject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val getUserRatingUseCase: GetUserRatingUseCase,
    private val addUserRatingUseCase: AddUserRatingUseCase,
    private val deleteUserRatingUseCase: DeleteUserRatingUseCase
) : ViewModel() {

    private val _id = MutableLiveData<Long>()
    var data: LiveData<UserRatingUiModel>
        private set

    init {
        data = _id.switchMap { id ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
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
}
