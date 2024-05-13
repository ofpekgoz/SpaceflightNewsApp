package com.omerfpekgoz.spaceflightnewsapp.data.repository

import com.omerfpekgoz.spaceflightnewsapp.data.data_source.local.dao.ArticleDao
import com.omerfpekgoz.spaceflightnewsapp.data.data_source.remote.ArticleAPI
import com.omerfpekgoz.spaceflightnewsapp.data.model.toArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle
import com.omerfpekgoz.spaceflightnewsapp.domain.repository.ArticleRepository
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
class ArticleRepositoryImpl @Inject constructor(
    private val articleAPI: ArticleAPI,
    private val articleDao: ArticleDao
) : ArticleRepository {
    override suspend fun getArticleList(forceFetchFromRemote: Boolean)
            : List<ArticleEntity> {
        val localList = articleDao.getArticleList()
        val shouldLocalList = localList.isNotEmpty() && !forceFetchFromRemote
        if (shouldLocalList) return localList
        return try {
            val getArticleList = articleAPI.getArticleList().results
            val saveArticleList = getArticleList.map {
                it.toArticleEntity()
            }
            articleDao.upsertArticleList(saveArticleList)
            saveArticleList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getArticleById(id: Int, forceFetchFromRemote: Boolean): ArticleEntity? {
        val localArticle = articleDao.getArticleById(id)
        localArticle?.let {
            val shouldLocalArticle = !forceFetchFromRemote
            if (shouldLocalArticle) return localArticle
        }
        return try {
            val getArticle = articleAPI.getArticleById(id).toArticleEntity()
            getArticle
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getFavoriteArticleList(): List<FavoriteArticle> {
        return articleDao.getFavoriteArticleList()
    }

    override suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        return articleDao.insertFavoriteArticle(favoriteArticle)
    }

    override fun deleteFavoriteArticle(id: Int) {
        articleDao.deleteFavoriteArticle(id)
    }

    override suspend fun isArticleInFavorites(id: Int): FavoriteArticle? {
        return articleDao.isArticleInFavorites(id)
    }
}