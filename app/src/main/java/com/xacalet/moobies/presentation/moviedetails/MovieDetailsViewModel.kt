package com.xacalet.moobies.presentation.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xacalet.domain.model.MovieDetails
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getImageUrlUseCase: GetImageUrlUseCase
) : ViewModel() {

    private val _id = MutableLiveData<Long>()
    private val _posterImageWidth = MutableLiveData<Int>()
    private val _posterFilePath = MutableLiveData<String>()
    private val _backdropImageWidth = MutableLiveData<Int>()
    private val _backdropFilePath = MutableLiveData<String>()

    private val _details: LiveData<MovieDetails> = _id.switchMap { id ->
        liveData(viewModelScope.coroutineContext) {
            emit(getMovieDetailsUseCase(id))
        }
    }

    val details: LiveData<MovieDetails> = _details
    val posterUrlImage: MediatorLiveData<String> = MediatorLiveData()
    val backdropUrlImage: MediatorLiveData<String> = MediatorLiveData()

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

