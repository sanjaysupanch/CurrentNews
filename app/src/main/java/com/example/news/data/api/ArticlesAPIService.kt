package com.example.news.data.api

import com.example.news.data.modal.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesAPIService {

    @GET("v2/top-headlines")
    suspend fun getArticles(
        @Query("country") country: String = "us",
        @Query("q") query: String? = null,
        @Query("apiKey") apiKey: String
    ): ArticlesResponse
}