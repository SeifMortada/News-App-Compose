package com.example.newsappcompose.article.presentation.article

import com.example.newsappcompose.core.domain.Article


/**
 * UI State that represents ArticleScreen
 **/
data class ArticleState(
    val article: Article? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

/**
 * Article Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
sealed interface ArticleActions {
    data class LoadArticle(val articleId: String) : ArticleActions
}


