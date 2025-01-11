package com.example.newsappcompose.news.di

import com.example.newsappcompose.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule= module{
    viewModel{ NewsViewModel(newsRepository = get()) }
}