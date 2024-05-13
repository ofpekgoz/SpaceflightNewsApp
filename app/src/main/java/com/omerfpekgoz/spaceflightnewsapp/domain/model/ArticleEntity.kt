package com.omerfpekgoz.spaceflightnewsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omerfpekgoz.spaceflightnewsapp.data.model.Result

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
@Entity("SpaceflightNews")
data class ArticleEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val summary: String,
    val published_at: String,
    val image_url: String,
    val url: String


)

fun ArticleEntity.toFavoriteArticleEntity(): FavoriteArticle {
    return FavoriteArticle(
        id = id,
        title = title,
        summary = summary,
        published_at = published_at,
        image_url = image_url,
        url = url
    )
}