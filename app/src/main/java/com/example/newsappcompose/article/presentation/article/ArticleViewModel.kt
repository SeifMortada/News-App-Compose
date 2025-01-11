package com.example.newsappcompose.article.presentation.article

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsappcompose.core.domain.NewsRepository
import com.example.newsappcompose.core.domain.NewsResult
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    var state by mutableStateOf(ArticleState())
        private set

    fun loadArticle(articleId: String) {
        if (articleId.isBlank()) {
            state = state.copy(isError = true)
            Log.e("ArticleViewModel", "Invalid articleId: $articleId")
            return
        }
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            Log.d("ArticleViewModel", "Loading article for ID: $articleId")
            newsRepository.getArticle(articleId).collect { articleResult ->
                when (articleResult) {
                    is NewsResult.Error -> {
                        Log.e("ArticleViewModel", "Error loading article: ${articleResult.errorMessage}")
                        state = state.copy(isError = true)
                    }
                    is NewsResult.Success -> {
                        Log.d("ArticleViewModel", "Article loaded: ${articleResult.data}")
                        state = state.copy(article = articleResult.data, isError = false, isLoading = false)
                        Log.d("Article " , "State is after success $state")
                    }
                }
            }
            state = state.copy(isLoading = false)
        }
    }

}