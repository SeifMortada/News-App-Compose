package com.example.newsappcompose.article.presentation.article

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.androidx.compose.koinViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class ArticleCoordinator(
    val viewModel: ArticleViewModel
) {
    val screenState = viewModel.state
    fun onAction(action: ArticleActions) {
        when (action) {
            is ArticleActions.LoadArticle -> viewModel.loadArticle(action.articleId)
        }

    }

}

@Composable
fun rememberArticleCoordinator(
    viewModel: ArticleViewModel = koinViewModel()
): ArticleCoordinator {
    return remember(viewModel) {
        ArticleCoordinator(
            viewModel = viewModel
        )
    }
}