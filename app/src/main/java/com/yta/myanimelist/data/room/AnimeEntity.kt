package com.yta.myanimelist.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yta.myanimelist.domain.models.AnimeData

@Entity(tableName = "animes")
data class AnimeEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "rating") val rating: Double?,
    @ColumnInfo(name = "poster_image_url") val posterImageUrl: String?,
    @ColumnInfo(name = "trailer_link") val trailerLink: String?,
    @ColumnInfo(name = "plot") val plot: String?,
    @ColumnInfo(name = "genres") val genres: String?,
    @ColumnInfo(name = "episodes") val episodes: Int?
)

fun AnimeEntity.toAnimeData() = AnimeData(
    id = id,
    title = title,
    rating = rating,
    posterImageUrl = posterImageUrl,
    trailerLink = trailerLink,
    plot = plot,
    genres = genres,
    episodes = episodes
)

fun AnimeData.toAnimeEntity() = AnimeEntity(
    id = id,
    title = title,
    rating = rating,
    posterImageUrl = posterImageUrl,
    trailerLink = trailerLink,
    plot = plot,
    genres = genres,
    episodes = episodes
)
