package com.omerfpekgoz.spaceflightnewsapp.domain.use_case

import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle
import com.omerfpekgoz.spaceflightnewsapp.domain.repository.ArticleRepository
import javax.inject.Inject

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
class InsertArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        articleRepository.insertFavoriteArticle(favoriteArticle)
    }
}