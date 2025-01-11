package com.example.newsappcompose.article.presentation.article

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


@Composable
fun ArticleRoute(
    coordinator: ArticleCoordinator = rememberArticleCoordinator(),
    articleId: String
) {
    val uiState = coordinator.screenState
    val actions = rememberArticleActions(coordinator)

    LaunchedEffect(Unit) {
        actions(ArticleActions.LoadArticle(articleId))
    }

    ArticleScreen(state = uiState, actions = { action -> actions(action) }, articleId = articleId)
}


@Composable
fun rememberArticleActions(coordinator: ArticleCoordinator): (ArticleActions) -> Unit {
    return remember(coordinator) { { action -> coordinator.onAction(action) } }
}
