package com.yta.myanimelist.domain.models

data class AnimeData(
    val id: Long,
    val title: String? = null,
    val rating: String? = null,
    val posterImageUrl: String? = null,
    val trailerLink: String? = null,
    val plot: String? = null,
    val genres: String? = null,
    val episodes: Int? = null
)