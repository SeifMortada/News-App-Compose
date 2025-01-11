package com.example.newsappcompose.news.presentation

sealed interface NewsAction {
    data object Paginate : NewsAction
}