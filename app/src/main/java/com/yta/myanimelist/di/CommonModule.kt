package com.yta.myanimelist.di

import androidx.room.Room
import com.yta.myanimelist.data.remote.NetworkUtils
import com.yta.myanimelist.data.room.AnimeDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::NetworkUtils)

    single<AnimeDatabase> {
        Room.databaseBuilder(
            get(),
            AnimeDatabase::class.java,
            "anime_db"
        ).build()
    }
}