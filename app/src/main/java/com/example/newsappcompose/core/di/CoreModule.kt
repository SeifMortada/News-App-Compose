package com.example.newsappcompose.core.di

import androidx.room.Room
import com.example.newsappcompose.core.data.local.ArticleDatabase
import com.example.newsappcompose.core.data.repository.NewsRepositoryImpl
import com.example.newsappcompose.core.domain.NewsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    single {
        Room.databaseBuilder(androidApplication(), ArticleDatabase::class.java, "article_db")
            .build()
    }
    single {
        get<ArticleDatabase>().dao
    }

    single {
        ktorClient
    }

    singleOf(::NewsRepositoryImpl).bind<NewsRepository>()
}