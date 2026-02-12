package com.yta.myanimelist.presentation.animeListing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yta.myanimelist.domain.AnimeRepository
import com.yta.myanimelist.domain.models.AnimeData
import com.yta.myanimelist.domain.util.GenericStatusCode
import com.yta.myanimelist.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeListingViewModel(
    private val repo: AnimeRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errors: MutableSharedFlow<String?> = MutableSharedFlow()
    val errors = _errors.asSharedFlow()

    private val _animeList = MutableStateFlow<List<AnimeData>>(emptyList())
    val animeList: StateFlow<List<AnimeData>> = _animeList

    fun fetchTopAnimes() {
        viewModelScope.launch(ioDispatcher) {
            _isLoading.value = true
            when (val resource = repo.getTopAnime()) {
                is Resource.Success -> {
                    _animeList.value = resource.data
                }

                is Resource.Error -> {
                    val errorMsg = if (resource.statusCode != GenericStatusCode.NoInternet) {
                        "Something went wrong. Please retry again after some time"
                    } else {
                        "Please connect to Internet"
                    }
                    _errors.emit(errorMsg)
                }
            }
            _isLoading.value = false
        }
    }
}