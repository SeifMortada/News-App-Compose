package com.example.newsappcompose.core.data.remote.request
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val article_id: String?,
    val title: String?,
    val description: String?,
    val content: String?,
    val pubDate: String?,
    val source_name: String?,
    val image_url: String?
)