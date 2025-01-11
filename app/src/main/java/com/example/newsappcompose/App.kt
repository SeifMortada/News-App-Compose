package com.example.newsappcompose

import android.app.Application
import com.example.newsappcompose.core.di.coreModule
import com.example.newsappcompose.news.di.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(coreModule, newsModule)
        }
    }
}