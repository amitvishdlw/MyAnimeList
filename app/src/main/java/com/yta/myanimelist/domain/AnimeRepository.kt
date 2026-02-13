package com.yta.myanimelist.domain

import com.yta.myanimelist.domain.models.AnimeData
import com.yta.myanimelist.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getTopAnime(): Flow<List<AnimeData>>

    suspend fun fetchTopAnime(
        page: Int,
        limit: Int
    ): Resource<Unit>

    fun getAnimeDetail(animeId: Long): Flow<AnimeData>

    suspend fun fetchAnimeDetail(animeId: Long): Resource<Unit>
}