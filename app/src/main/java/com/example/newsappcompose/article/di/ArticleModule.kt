package com.example.newsappcompose.article.di

import com.example.newsappcompose.article.presentation.article.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val articleModule = module {
    viewModel { ArticleViewModel(get()) }
}