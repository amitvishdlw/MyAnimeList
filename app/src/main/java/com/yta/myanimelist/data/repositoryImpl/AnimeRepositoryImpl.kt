package com.yta.myanimelist.data.repositoryImpl

import com.yta.myanimelist.data.models.toAnimeData
import com.yta.myanimelist.data.remote.ApiService
import com.yta.myanimelist.data.remote.NetworkUtils
import com.yta.myanimelist.data.room.AnimeDatabase
import com.yta.myanimelist.data.room.toAnimeData
import com.yta.myanimelist.data.room.toAnimeEntity
import com.yta.myanimelist.domain.AnimeRepository
import com.yta.myanimelist.domain.models.AnimeData
import com.yta.myanimelist.domain.util.GenericStatusCode
import com.yta.myanimelist.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.IOException

class AnimeRepositoryImpl(
    private val apiService: ApiService,
    private val networkUtils: NetworkUtils,
    private val animeDb: AnimeDatabase
) : AnimeRepository {
    override fun getTopAnime(): Flow<List<AnimeData>> =
        animeDb.animeDao().getAll().map {
            return@map it.map { animeEntity ->
                animeEntity.toAnimeData()
            }
        }

    override suspend fun fetchTopAnime(page: Int, limit: Int): Resource<Unit> =
        tryNetworkCallForResource {
            val httpResponse = apiService.getTopAnime(
                page = page,
                limit = limit
            )
            if (httpResponse.isSuccessful) {
                httpResponse.body()?.data?.let { animeList ->
                    val animes = animeList.map { it.toAnimeData() }
                    animeDb.animeDao().insertAll(*animes.map { it.toAnimeEntity() }.toTypedArray())
                    return@tryNetworkCallForResource Resource.success(Unit)
                }
            }
            return@tryNetworkCallForResource Resource.errorGeneric()
        }

    override suspend fun fetchAnimeDetail(animeId: Long): Resource<Unit> =
        tryNetworkCallForResource {
            val httpResponse = apiService.getAnimeDetail(animeId)
            if (httpResponse.isSuccessful) {
                httpResponse.body()?.let {
                    val anime = it.data.toAnimeData()
                    animeDb.animeDao().insertAll(anime.toAnimeEntity())
                    return@tryNetworkCallForResource Resource.success(Unit)
                }
            }
            return@tryNetworkCallForResource Resource.errorGeneric()
        }

    override fun getAnimeDetail(animeId: Long): Flow<AnimeData> =
        animeDb.animeDao().findById(animeId).map {
            return@map it.toAnimeData()
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