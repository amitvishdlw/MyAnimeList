package com.yta.myanimelist.data.models

import com.yta.myanimelist.domain.models.AnimeData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AnimeDetailDto(
    @SerialName("data") val data: AnimeMetaData
)
@Serializable
data class AnimeMetaData(
    @SerialName("mal_id") val id: Long,
    @SerialName("title") val title: String? = null,
    @SerialName("episodes") val episodes: Int? = null,
    @SerialName("score") val score: Double? = null,
    @SerialName("synopsis") val synopsis: String? = null,
    @SerialName("images") val images: ImagesDto? = null,
    @SerialName("trailer") val trailer: Trailer? = null,
    @SerialName("genres") val genres: List<Genre>? = null
) {
    @Serializable
    data class Trailer(
        @SerialName("embed_url")
        val embedUrl: String? = null
    )


    @Serializable
    data class ImagesDto(
        val jpg: ImageUrls? = null,
        val webp: ImageUrls? = null
    )


    @Serializable
    data class ImageUrls(

        @SerialName("image_url")
        val imageUrl: String? = null,

        @SerialName("small_image_url")
        val smallImageUrl: String? = null,

        @SerialName("large_image_url")
        val largeImageUrl: String? = null
    )

    @Serializable
    data class Genre(
        @SerialName("mal_id") val id: Long? = null,
        @SerialName("name") val name: String? = null,
    )

}

fun AnimeMetaData.toAnimeData() = AnimeData(
    id = id,
    title = title,
    rating = score,
    posterImageUrl = images?.webp?.imageUrl ?: images?.jpg?.imageUrl,
    trailerLink = trailer?.embedUrl,
    plot = synopsis,
    episodes = episodes,
    genres = genres?.mapNotNull { it.name }?.joinToString(", ")
)


