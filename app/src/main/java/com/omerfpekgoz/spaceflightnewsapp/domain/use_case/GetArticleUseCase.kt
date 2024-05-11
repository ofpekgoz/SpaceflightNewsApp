package com.omerfpekgoz.spaceflightnewsapp.domain.use_case

import android.util.Log
import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle
import com.omerfpekgoz.spaceflightnewsapp.domain.repository.ArticleRepository
import com.omerfpekgoz.spaceflightnewsapp.util.Resource
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
class GetArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    fun getArticleList(forceFetchFromRemote: Boolean) = flow {
        emit(Resource.Loading)
        try {
            val articleList = articleRepository.getArticleList(forceFetchFromRemote)
            emit(Resource.Success(articleList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
            return@flow
        }
    }

    fun getArticleById(id: Int, forceFetchFromRemote: Boolean) = flow {
        emit(Resource.Loading)
        try {
            Log.e("ARTICLE", "Use Case 1 $id")
            val article = articleRepository.getArticleById(id, forceFetchFromRemote)
            Log.e("ARTICLE", "Use Case $article")
            emit(Resource.Success(article))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
            return@flow
        }
    }

    fun getFavoriteArticleList() = flow {
        emit(Resource.Loading)
        try {
            val favoriteArticleList = articleRepository.getFavoriteArticleList()
            emit(Resource.Success(favoriteArticleList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
            return@flow
        }
    }

    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        articleRepository.insertFavoriteArticle(favoriteArticle)
    }

    suspend fun deleteFavoriteArticle(id: Int) {
        articleRepository.deleteFavoriteArticle(id)
    }
}