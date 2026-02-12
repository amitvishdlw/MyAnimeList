package com.yta.myanimelist.di

import com.yta.myanimelist.data.repositoryImpl.AnimeRepositoryImpl
import com.yta.myanimelist.domain.AnimeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::AnimeRepositoryImpl) bind AnimeRepository::class
}