package com.example.newsappcompose.news

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.core.domain.NewsRepository
import com.example.newsappcompose.core.domain.NewsResult
import com.example.newsappcompose.news.presentation.NewsAction
import com.example.newsappcompose.news.presentation.NewsState
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    var state by mutableStateOf(NewsState())
        private set

    fun onAction(action: NewsAction) {
        when (action) {
            NewsAction.Paginate -> paginate()
        }
    }

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            newsRepository.getNews().collect { newsResult ->
                state = when (newsResult) {
                    is NewsResult.Error -> {
                        state.copy(isError = true)
                    }

                    is NewsResult.Success -> {
                        state.copy(
                            articlesList = newsResult.data?.articles ?: emptyList(),
                            nextPage = newsResult.data?.nextPage,
                            isError = false
                        )
                    }
                }
            }
            state = state.copy(isLoading = false)
        }
    }

    private fun paginate() {
        viewModelScope.launch {
            newsRepository.paginate(state.nextPage).collect { newsResult ->
                state = when (newsResult) {
                    is NewsResult.Error -> {
                        state.copy(isError = true)
                    }

                    is NewsResult.Success -> {
                        val updatedArticles = newsResult.data?.articles ?: emptyList()
                        state.copy(
                            articlesList = state.articlesList + updatedArticles,
                            nextPage = newsResult.data?.nextPage + 1,
                            isError = false
                        )
                    }
                }
            }
            state = state.copy(isLoading = false)
        }
    }
}