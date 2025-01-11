package com.example.newsappcompose.core.data.mappers

import com.example.newsappcompose.core.data.local.ArticleEntity
import com.example.newsappcompose.core.domain.Article

fun ArticleEntity.toArticle(): Article {
    return Article(
        articleId = articleId,
        title = title,
        description = description,
        content = content,
        pubDate = pubDate,
        sourceName = sourceName,
        imageUrl = imageUrl
    )
}

fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        articleId = articleId,
        title = title,
        description = description,
        pubDate = pubDate,
        sourceName = sourceName,
        imageUrl = imageUrl,
        content = content
    )
}