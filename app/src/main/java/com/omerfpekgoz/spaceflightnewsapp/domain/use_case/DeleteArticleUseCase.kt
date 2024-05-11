package com.omerfpekgoz.spaceflightnewsapp.domain.use_case

import com.omerfpekgoz.spaceflightnewsapp.domain.repository.ArticleRepository
import javax.inject.Inject

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
class DeleteArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend fun deleteFavoriteArticle(id: Int) {
        articleRepository.deleteFavoriteArticle(id)
    }
}