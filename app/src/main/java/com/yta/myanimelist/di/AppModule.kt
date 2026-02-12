package com.yta.myanimelist.di

fun appModule() = listOf(
    commonModule,
    networkModule,
    repositoryModule,
    viewModelModule
)