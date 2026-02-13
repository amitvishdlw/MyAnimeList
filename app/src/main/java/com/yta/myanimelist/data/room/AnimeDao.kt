package com.yta.myanimelist.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM animes")
    fun getAll(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM animes WHERE id == :animeId")
    fun findById(animeId: Long): Flow<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg animes: AnimeEntity)

    @Delete
    suspend fun delete(anime: AnimeEntity)
}