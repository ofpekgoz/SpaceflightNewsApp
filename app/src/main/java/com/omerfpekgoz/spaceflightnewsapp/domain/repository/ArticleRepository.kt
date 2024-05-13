package com.omerfpekgoz.spaceflightnewsapp.domain.repository

import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
interface ArticleRepository {
    suspend fun getArticleList(
        forceFetchFromRemote: Boolean,
    ): List<ArticleEntity>

    suspend fun getArticleById(
        id: Int,
        forceFetchFromRemote: Boolean
    ): ArticleEntity?


    suspend fun getFavoriteArticleList(): List<FavoriteArticle>
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle)

     fun deleteFavoriteArticle(id: Int)

    suspend fun isArticleInFavorites(id: Int): FavoriteArticle?
}