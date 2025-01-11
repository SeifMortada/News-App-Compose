package com.example.newsappcompose.core.data.repository

import com.example.newsappcompose.core.data.local.ArticlesDao
import com.example.newsappcompose.core.data.mappers.toArticleEntity
import com.example.newsappcompose.core.data.mappers.toNews
import com.example.newsappcompose.core.data.mappers.toNewsList
import com.example.newsappcompose.core.data.remote.request.NewsListDto
import com.example.newsappcompose.core.data.utils.CoreConstants.API_KEY
import com.example.newsappcompose.core.data.utils.CoreConstants.API_KEY_QUERY
import com.example.newsappcompose.core.data.utils.CoreConstants.BASE_URL
import com.example.newsappcompose.core.data.utils.CoreConstants.ENGLISH
import com.example.newsappcompose.core.data.utils.CoreConstants.LANGUAGE_QUERY
import com.example.newsappcompose.core.data.utils.CoreConstants.PAGE_QUERY
import com.example.newsappcompose.core.domain.Article
import com.example.newsappcompose.core.domain.NewsList
import com.example.newsappcompose.core.domain.NewsRepository
import com.example.newsappcompose.core.domain.NewsResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val dao: ArticlesDao
) : NewsRepository {
    private val tag = "NewsRepository: "

    private suspend fun loadLocalNews(nextPage: String?): NewsList {
        val localNews = dao.getArticlesList()
        print(tag + "loadLocalNews: " + localNews.size + " next page: " + nextPage)
        val newsList = NewsList(
            nextPage = nextPage,
            articles = localNews.map { it.toNews() }
        )
        return newsList
    }

    private suspend fun loadRemoteNews(nextPage: String?): NewsList {
        val newsListDto: NewsListDto = httpClient.get(BASE_URL) {
            parameter(API_KEY_QUERY, API_KEY)
            parameter(LANGUAGE_QUERY, ENGLISH)
            if (nextPage != null) parameter(PAGE_QUERY, nextPage)
        }.body()
        print(tag + "loadRemoteNews: " + newsListDto.results?.size + " next page: " + nextPage)
        return newsListDto.toNewsList()
    }

    override suspend fun getNews(): Flow<NewsResult<NewsList>> {
        return flow {
            val remoteNews = try {
                loadRemoteNews(nextPage = null)
            } catch (e: Exception) {
                e.printStackTrace()
                if(e is CancellationException) throw e
                print(tag + " getNews remote exception:" + e.message)
                null
            }
            remoteNews?.let { news ->
                dao.clearDatabase()
                dao.upsertArticleList(remoteNews.articles.map { it.toArticleEntity() })
                emit(NewsResult.Success(data = loadLocalNews(news.nextPage)))
                return@flow
            }

            val localNews = loadLocalNews(null)
            if (localNews.articles.isNotEmpty()) {
                emit(NewsResult.Success(data = localNews))
                return@flow
            }
            emit(NewsResult.Error(errorMessage = "No data"))
        }
    }

    override suspend fun paginate(nextPage: String?): Flow<NewsResult<NewsList>> {
        return flow {
            val remoteNews = try {
                loadRemoteNews(nextPage = nextPage)
            } catch (e: Exception) {
                e.printStackTrace()
                if(e is CancellationException) throw e
                print(tag + " paginate remote exception:" + e.message)
                null
            }
            remoteNews?.let { news ->
                dao.upsertArticleList(remoteNews.articles.map { it.toArticleEntity() })
                // Return the last fetched news
                emit(NewsResult.Success(data = remoteNews))
                return@flow
            }
        }
    }

    override suspend fun getArticle(articleId: String): Flow<NewsResult<Article>> {
        TODO("Not yet implemented")
    }
}