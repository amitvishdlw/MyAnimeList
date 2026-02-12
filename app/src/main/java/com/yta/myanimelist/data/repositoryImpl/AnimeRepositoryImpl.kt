package com.yta.myanimelist.data.repositoryImpl

import com.yta.myanimelist.data.models.toAnimeData
import com.yta.myanimelist.data.remote.ApiService
import com.yta.myanimelist.data.remote.NetworkUtils
import com.yta.myanimelist.domain.AnimeRepository
import com.yta.myanimelist.domain.models.AnimeData
import com.yta.myanimelist.domain.util.GenericStatusCode
import com.yta.myanimelist.domain.util.Resource
import okio.IOException

class AnimeRepositoryImpl(
    private val apiService: ApiService,
    private val networkUtils: NetworkUtils
) : AnimeRepository {
    override suspend fun getTopAnime(): Resource<List<AnimeData>> = tryNetworkCallForResource {
        val httpResponse = apiService.getTopAnime()
        if (httpResponse.isSuccessful) {
            httpResponse.body()?.data?.let { animeList ->
                return@tryNetworkCallForResource Resource.success(animeList.map { it.toAnimeData() })
            }
        }
        return@tryNetworkCallForResource Resource.errorGeneric()
    }

    override suspend fun getAnimeDetail(animeId: Int): Resource<AnimeData> =
        tryNetworkCallForResource {
        val httpResponse = apiService.getAnimeDetail(animeId)
        if (httpResponse.isSuccessful) {
            httpResponse.body()?.let {
                return@tryNetworkCallForResource Resource.success(it.toAnimeData())
            }
        }
            return@tryNetworkCallForResource Resource.errorGeneric()
        }

    private suspend fun <R> tryNetworkCallForResource(block: suspend () -> Resource<R>): Resource<R> {
        return try {
            if (!networkUtils.isNetworkAvailable()) {
                return Resource.Error(GenericStatusCode.NoInternet)
            }

            return block()
        } catch (e: Exception) {
            if (e is IOException) {
                Resource.Error(GenericStatusCode.NoInternet)
            } else {
                Resource.Error(GenericStatusCode.ApiFailed)
            }
        }
    }
}