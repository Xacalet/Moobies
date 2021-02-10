package com.xacalet.moobies.presentation.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.GetMovieDetailsUseCase
import com.xacalet.domain.usecase.GetUserRatingFlowUseCase
import com.xacalet.domain.usecase.IsWishlistedFlowUseCase
import com.xacalet.domain.usecase.ToggleWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val isWishlistedFlowUseCase: IsWishlistedFlowUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase,
    private val getUserRatingFlowUseCase: GetUserRatingFlowUseCase
) : ViewModel() {

    private val _id = MutableLiveData<Long>()
    private val _posterImageWidth = MutableLiveData<Int>()
    private val _posterFilePath = MutableLiveData<String>()
    private val _backdropImageWidth = MutableLiveData<Int>()
    private val _backdropFilePath = MutableLiveData<String>()

    private val _toggledWishlist: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    private val _details: LiveData<MovieDetails> = _id.switchMap { id ->
        liveData(viewModelScope.coroutineContext) {
            emit(getMovieDetailsUseCase(id))
        }
    }

    val details: LiveData<MovieDetails> = _details
    val posterUrlImage: MediatorLiveData<String> = MediatorLiveData()
    val backdropUrlImage: MediatorLiveData<String> = MediatorLiveData()
    val isWishlisted: LiveData<Boolean> = _id.switchMap { id ->
        isWishlistedFlowUseCase(id).asLiveData()
    }
    val userRating: LiveData<Int?> = _id.switchMap { id ->
        getUserRatingFlowUseCase(id).asLiveData()
    }

    init {
        listOf(_posterImageWidth, _posterFilePath).forEach { source ->
            posterUrlImage.addSource(source) {
                getImageUrl(_posterImageWidth.value, _posterFilePath.value, posterUrlImage)
            }
        }
        listOf(_backdropImageWidth, _backdropFilePath).forEach { source ->
            backdropUrlImage.addSource(source) {
                getImageUrl(_backdropImageWidth.value, _backdropFilePath.value, backdropUrlImage)
            }
        }
    }

    fun setPosterImageParams(imageWidth: Int, filePath: String) {
        if (_posterImageWidth.value != imageWidth) {
            _posterImageWidth.value = imageWidth
        }
        if (_posterFilePath.value != filePath) {
            _posterFilePath.value = filePath
        }
    }

    fun setBackdropImageParams(imageWidth: Int, filePath: String) {
        if (_backdropImageWidth.value != imageWidth) {
            _backdropImageWidth.value = imageWidth
        }
        if (_backdropFilePath.value != filePath) {
            _backdropFilePath.value = filePath
        }
    }

    fun setId(id: Long) {
        if (_id.value != id) {
            _id.value = id
        }
    }

    fun toggleWishlist() {
        _id.value?.let { id ->
            viewModelScope.launch {
                val isWishlisted = toggleWishlistUseCase(id)
                _toggledWishlist.value = isWishlisted
            }
        }
    }

    private fun getImageUrl(
        imageWidth: Int?,
        filePath: String?,
        targetLiveData: MutableLiveData<String>
    ) {
        if (imageWidth != null && filePath != null) {
            viewModelScope.launch {
                val url = getImageUrlUseCase(imageWidth, filePath)
                targetLiveData.value = url
            }
        }
    }
}
