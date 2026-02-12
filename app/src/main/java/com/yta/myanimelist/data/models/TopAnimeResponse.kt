package com.yta.myanimelist.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopAnimeResponse(
    @SerialName("data") val data: List<AnimeMetaData>
)


