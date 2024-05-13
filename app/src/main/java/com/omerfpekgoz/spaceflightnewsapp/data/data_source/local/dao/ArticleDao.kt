package com.omerfpekgoz.spaceflightnewsapp.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
@Dao
interface ArticleDao {

    @Upsert
    suspend fun upsertArticleList(articleList: List<ArticleEntity>)

    @Query("SELECT * FROM SpaceflightNews")
    suspend fun getArticleList(): List<ArticleEntity>

    @Query("SELECT * FROM SpaceflightNews WHERE id = :id")
    suspend fun getArticleById(id: Int): ArticleEntity?

    @Query("SELECT * FROM FavoriteArticle")
    suspend fun getFavoriteArticleList(): List<FavoriteArticle>

    @Query("SELECT * FROM FavoriteArticle WHERE id = :id LIMIT 1")
    fun isArticleInFavorites(id: Int): FavoriteArticle?

    @Upsert
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle)

    @Query("DELETE FROM FavoriteArticle WHERE id = :id")
    fun deleteFavoriteArticle(id: Int)
}