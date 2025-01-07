package com.example.newsappcompose.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], exportSchema = false, version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract val dao: ArticlesDao
}