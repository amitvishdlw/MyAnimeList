package com.yta.myanimelist.data.remote

import com.yta.myanimelist.data.models.AnimeDetailDto
import com.yta.myanimelist.data.models.TopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): Response<TopAnimeResponse>

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetail(
        @Path("anime_id") animeId: Long
    ): Response<AnimeDetailDto>
}