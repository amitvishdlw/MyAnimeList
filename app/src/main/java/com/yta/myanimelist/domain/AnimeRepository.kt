package com.yta.myanimelist.domain

import com.yta.myanimelist.domain.models.AnimeData
import com.yta.myanimelist.domain.util.Resource

interface AnimeRepository {
    suspend fun getTopAnime(): Resource<List<AnimeData>>

    suspend fun getAnimeDetail(animeId: Long): Resource<AnimeData>
}