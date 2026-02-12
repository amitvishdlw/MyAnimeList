package com.yta.myanimelist.di

import com.yta.myanimelist.data.remote.NetworkUtils
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::NetworkUtils)
}