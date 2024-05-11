package com.omerfpekgoz.spaceflightnewsapp.data.model
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity

data class Result(
    val events: List<Event>,
    val featured: Boolean,
    val id: Int,
    val image_url: String,
    val launches: List<Launche>,
    val news_site: String,
    val published_at: String,
    val summary: String,
    val title: String,
    val updated_at: String,
    val url: String
)

fun Result.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        summary = summary,
        published_at = published_at,
        image_url = image_url,
        url = url
    )
}

