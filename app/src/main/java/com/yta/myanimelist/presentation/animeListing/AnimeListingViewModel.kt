package com.yta.myanimelist.presentation.animeListing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yta.myanimelist.domain.AnimeRepository
import com.yta.myanimelist.domain.models.AnimeData
import com.yta.myanimelist.domain.util.GenericStatusCode
import com.yta.myanimelist.domain.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeListingViewModel(
    private val repo: AnimeRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errors: MutableSharedFlow<String?> = MutableSharedFlow()
    val errors = _errors.asSharedFlow()

    private val nextPage = MutableStateFlow(1)

    private val _animeList = MutableStateFlow<List<AnimeData>>(emptyList())
    val animeList: StateFlow<List<AnimeData>> = _animeList

    fun fetchTopAnimesAsync() {
        viewModelScope.launch(ioDispatcher) {
            _isLoading.value = true
            fetchTopAnimes(page = nextPage.value)
            _isLoading.value = false
        }
    }

    private suspend fun fetchTopAnimes(page: Int) {
        var isSuccess = false
        var exponentialBackOff = 0L

        while (!isSuccess) {
            delay(exponentialBackOff)

            when (val resource = repo.getTopAnime(
                page = page,
                limit = PAGE_LIMIT
            )) {
                is Resource.Success -> {
                    _animeList.update { it + resource.data }
                    nextPage.update { it + 1 }

                    isSuccess = true
                }

                is Resource.Error -> {
                    val errorMsg = if (resource.statusCode != GenericStatusCode.NoInternet) {
                        "Something went wrong. Please retry again after some time"
                    } else {
                        "Please connect to Internet"
                    }
                    _errors.emit(errorMsg)

                    exponentialBackOff = if (exponentialBackOff == 0L) {
                        1000L
                    } else {
                        2 * exponentialBackOff
                    }
                }
            }
        }
    }

    companion object {
        private const val PAGE_LIMIT = 25
    }
}