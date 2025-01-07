package com.example.newsappcompose.core.data.mappers

import com.example.newsappcompose.core.data.remote.request.ArticleDto
import com.example.newsappcompose.core.data.remote.request.NewsListDto
import com.example.newsappcompose.core.domain.Article
import com.example.newsappcompose.core.domain.NewsList

fun NewsListDto.toNewsList(): NewsList {
    return NewsList(
        nextPage = nextPage?:"",
        articles = results?.map { it.toNewsList() } ?: emptyList()
    )
}

fun ArticleDto.toNewsList(): Article {
    return Article(
        articleId = article_id ?: "",
        title = title ?: "",
        description = description ?: "",
        content = content ?: "$article_id  PlaceHolder for the content",
        pubDate = pubDate ?: "",
        sourceName = source_name ?: "",
        imageUrl = image_url ?: ""
    )
}