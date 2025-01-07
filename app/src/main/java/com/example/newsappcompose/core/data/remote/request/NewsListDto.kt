package com.example.newsappcompose.core.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class NewsListDto(
    val nextPage: String?,
    val results: List<ArticleDto>?,
)