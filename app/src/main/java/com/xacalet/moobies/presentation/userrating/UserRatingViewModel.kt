package com.xacalet.moobies.presentation.userrating

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xacalet.domain.usecase.AddUserRatingUseCase
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.GetMovieDetailsUseCase
import com.xacalet.domain.usecase.GetUserRatingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserRatingViewModel @ViewModelInject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val getUserRatingUseCase: GetUserRatingUseCase,
    private val addUserRatingUseCase: AddUserRatingUseCase
) : ViewModel() {

    private val _id = MutableLiveData<Long>()
    var data: LiveData<UserRatingUiModel>
        private set

    init {
        data = _id.switchMap { id ->
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                val imageUrl = getMovieDetailsUseCase(id).posterPath?.let { filePath ->
                    getImageUrlUseCase(200, filePath)
                }
                val stars = getUserRatingUseCase(id)
                delay(1000) //Just to give visibility to the progress bar
                emit(UserRatingUiModel(id, stars, imageUrl))
            }
        }
    }

    fun setId(id: Long) {
        if (_id.value != id) {
            _id.value = id
        }
    }

    fun addUserRating(stars: Byte) {
        _id.value?.let { id ->
            viewModelScope.launch {
                addUserRatingUseCase(id, stars)
            }
        }
    }
}
