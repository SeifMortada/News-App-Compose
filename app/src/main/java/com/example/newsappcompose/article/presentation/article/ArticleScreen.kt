package com.example.newsappcompose.article.presentation.article

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.newsappcompose.core.domain.Article

@Composable
fun ArticleScreen(
    state: ArticleState,
    actions: (ArticleActions) -> Unit,
    articleId: String
) {
//    LaunchedEffect(true) {
//        actions(ArticleActions.LoadArticle(articleId))
//    }
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading && state.article == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            if (state.article == null && !state.isLoading) {
                Log.d("Article " , "State is $state")
                Text(text = "No article found", style = MaterialTheme.typography.bodyMedium)
            }

            if (state.isError) {
                Text(
                    text = "Error loading article",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
            state.article?.let { article ->
                ArticleDetails(article)
            }
        }
    }
}

@Composable
fun ArticleDetails(article: Article, modifier: Modifier = Modifier) {
    Log.d("ArticleDetails", "Rendering article: ${article.title}")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(vertical = 22.dp)
    ) {
        Text(text = article.title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = article.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = article.pubDate, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = article.imageUrl,
            contentDescription = article.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(250.dp)
        )
        Text(text = article.content, style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
@Preview(name = "Article")
private fun ArticleScreenPreview() {
    ArticleScreen(
        state = ArticleState(),
        actions = {},
        ""
    )
}

