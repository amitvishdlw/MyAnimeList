package com.yta.myanimelist

import android.app.Application
import com.yta.myanimelist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyAnimeListApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyAnimeListApp)
            modules(appModule())
        }
    }
}