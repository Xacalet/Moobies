package com.xacalet.moobies.presentation.showdetails

import androidx.lifecycle.*
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.usecase.*
import com.xacalet.moobies.presentation.navigation.CommonDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val isWishlistedFlowUseCase: IsWishlistedFlowUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase,
    private val getUserRatingFlowUseCase: GetUserRatingFlowUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _id =
        MutableLiveData<Long>(savedStateHandle.get(CommonDirections.ShowDetails.SHOW_ID))
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
