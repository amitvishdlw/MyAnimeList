package com.yta.myanimelist.presentation.animeDetail

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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val animeId: Long,
    private val repo: AnimeRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errors: MutableSharedFlow<String?> = MutableSharedFlow()
    val errors = _errors.asSharedFlow()

    private val _animeData = repo.getAnimeDetail(animeId).stateIn(
        viewModelScope,
        initialValue = null,
        started = SharingStarted.WhileSubscribed(5000)
    )
    val animeData: StateFlow<AnimeData?> = _animeData

    fun fetchAnime() {
        viewModelScope.launch(ioDispatcher) {
            _isLoading.value = true
            when (val resource = repo.fetchAnimeDetail(animeId)) {
                is Resource.Success -> {}

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