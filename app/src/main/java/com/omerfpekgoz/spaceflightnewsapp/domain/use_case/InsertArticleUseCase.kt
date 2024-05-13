package com.omerfpekgoz.spaceflightnewsapp.domain.use_case

import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle
import com.omerfpekgoz.spaceflightnewsapp.domain.repository.ArticleRepository
import com.omerfpekgoz.spaceflightnewsapp.util.Resource
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
class InsertArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) = flow {
        emit(Resource.Loading)
        try {
            articleRepository.insertFavoriteArticle(favoriteArticle)
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
            return@flow
        }
    }
}