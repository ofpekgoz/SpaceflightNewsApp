package com.omerfpekgoz.spaceflightnewsapp.data.data_source.remote

import com.omerfpekgoz.spaceflightnewsapp.data.model.Result
import com.omerfpekgoz.spaceflightnewsapp.data.model.ArticleListDTO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
interface ArticleAPI {

    @GET("articles")
    suspend fun getArticleList(): ArticleListDTO

    @GET("articles/{id}")
    suspend fun getArticleById(
        @Path("id") id: Int,
    ): Result

    companion object {
        const val BASE_URL = "https://api.spaceflightnewsapi.net/v4/"
    }
}