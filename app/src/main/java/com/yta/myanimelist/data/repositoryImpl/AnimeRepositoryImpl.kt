package com.yta.myanimelist.data.repositoryImpl

import com.yta.myanimelist.data.models.toAnimeData
import com.yta.myanimelist.data.remote.ApiService
import com.yta.myanimelist.data.remote.NetworkUtils
import com.yta.myanimelist.domain.AnimeRepository
import com.yta.myanimelist.domain.models.AnimeData
import com.yta.myanimelist.domain.util.GenericStatusCode
import com.yta.myanimelist.domain.util.Resource

class AnimeRepositoryImpl(
    private val apiService: ApiService,
    private val networkUtils: NetworkUtils
) : AnimeRepository {
    override suspend fun getTopAnime(): Resource<List<AnimeData>> {
        if (!networkUtils.isNetworkAvailable()) {
            return Resource.Error(GenericStatusCode.NoInternet)
        }

        val httpResponse = apiService.getTopAnime()
        if (httpResponse.isSuccessful) {
            httpResponse.body()?.data?.let { animes ->
                return Resource.success(animes.map { it.toAnimeData() })
            }
        }
        return Resource.errorGeneric()
    }

    override suspend fun getAnimeDetail(animeId: Int): Resource<AnimeData> {
        val httpResponse = apiService.getAnimeDetail(animeId)
        if (httpResponse.isSuccessful) {
            httpResponse.body()?.let {
                return Resource.success(it.toAnimeData())
            }
        }
        return Resource.errorGeneric()
    }
}