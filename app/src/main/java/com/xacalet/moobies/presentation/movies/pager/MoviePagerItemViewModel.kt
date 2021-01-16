package com.xacalet.moobies.presentation.movies.pager

import androidx.lifecycle.*
import com.xacalet.domain.usecase.GetImageUrlUseCase
import com.xacalet.domain.usecase.IsWishlistedFlowUseCase
import com.xacalet.domain.usecase.ToggleWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviePagerItemViewModel @Inject constructor(
    private val getImageUrlUseCase: GetImageUrlUseCase,
    private val isWishlistedFlowUseCase: IsWishlistedFlowUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase
) : ViewModel() {

    private val _id = MutableLiveData<Long>()
    private val _posterImageWidth = MutableLiveData<Int>()
    private val _posterFilePath = MutableLiveData<String>()
    private val _backdropImageWidth = MutableLiveData<Int>()
    private val _backdropFilePath = MutableLiveData<String>()

    @ExperimentalCoroutinesApi
    private val _toggledWishlist: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    val posterUrlImage: MediatorLiveData<String> = MediatorLiveData()
    val backdropUrlImage: MediatorLiveData<String> = MediatorLiveData()
    val isWishlisted: LiveData<Boolean> = _id.switchMap { id ->
        isWishlistedFlowUseCase(id).asLiveData()
    }

    @ExperimentalCoroutinesApi
    val toggledWishlist: Flow<Boolean?>
        get() = _toggledWishlist

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

    @ExperimentalCoroutinesApi
    fun toggleWishlist(id: Long) {
        viewModelScope.launch {
            val isWishlisted = toggleWishlistUseCase(id)
            _toggledWishlist.value = isWishlisted
            // Emitting true or false through this flow makes UI show a toast. If the latest
            // value emitted is indeed one of these two values, when the observing fragment is
            // resumed, it'll collect it and an undesired toast will be displayed. To workaround
            // this issue, a null value is emitted after the regular value.
            // TODO: Consider other alternatives: SingleLiveData, LiveEvent wrapper
            _toggledWishlist.value = null
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