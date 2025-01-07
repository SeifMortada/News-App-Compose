package com.example.newsappcompose.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class NewsList(
    val nextPage: String?,
    val articles: List<Article>,
)