package com.example.newsappcompose.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM Articles_Table")
    suspend fun getArticlesList(): List<ArticleEntity>

    @Upsert
    suspend fun upsertArticleList(articleList: List<ArticleEntity>)

    @Query("SELECT * FROM Articles_Table WHERE articleId=:articleId")
    suspend fun getArticle(articleId: String): ArticleEntity?

    @Query("DELETE FROM Articles_Table")
    suspend fun clearDatabase()
}