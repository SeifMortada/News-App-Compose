package com.example.newsappcompose.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Articles_Table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val articleId: String,
    val title: String,
    val description: String,
    val content: String,
    val pubDate: String,
    val sourceName: String,
    val imageUrl: String
)