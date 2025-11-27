package com.example.news.data.repository

import com.example.news.BuildConfig
import com.example.news.data.api.ArticlesAPIService
import com.example.news.data.database.NewsDao
import com.example.news.data.modal.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: ArticlesAPIService,
    private val dao: NewsDao
) {
    /**
     * Fetches news articles from the API and emits the result as a Flow.
     *
     * @return A [Flow] emitting [Result] with a list of [Article] on success or an error on failure.
     */
    fun fetchNews(): Flow<Result<List<Article>>> = flow {
        try {
            val response = api.getArticles(
                apiKey = BuildConfig.NEWS_API_KEY
            )
            emit(Result.success(response.articles))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    // Search using "q" parameter
    fun searchNews(query: String): Flow<Result<List<Article>>> = flow {
        try {
            val response = api.getArticles(
                query = query,
                apiKey = BuildConfig.NEWS_API_KEY
            )
            emit(Result.success(response.articles))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getSavedArticles(): Flow<List<Article>> = dao.getSavedArticles()

    suspend fun saveArticle(article: Article) = dao.saveArticle(article)

    suspend fun deleteArticle(articleUrl: String) = dao.deleteArticle(articleUrl)

    // Fetch latest news once (Worker compatible)
    suspend fun fetchLatestNewsOnce(): Result<List<Article>> {
        return try {
            val response = api.getArticles(apiKey = BuildConfig.NEWS_API_KEY)
            Result.success(response.articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get only URLs (faster for comparison)
    suspend fun getSavedArticleUrls(): List<String> {
        return dao.getAllUrls()
    }

    // Save multiple new articles
    suspend fun saveArticles(list: List<Article>) {
        dao.insertAll(list)
    }
}