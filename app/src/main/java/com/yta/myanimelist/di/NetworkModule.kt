package com.yta.myanimelist.di

import com.yta.myanimelist.data.remote.ApiService
import com.yta.myanimelist.domain.StringConstants
import com.yta.myanimelist.domain.util.appJson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(StringConstants.BASE_URL)
            .addConverterFactory(appJson.asConverterFactory("application/json".toMediaType()))
            .client(get<OkHttpClient>())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}