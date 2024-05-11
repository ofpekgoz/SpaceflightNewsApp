package com.omerfpekgoz.spaceflightnewsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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


) {
    override fun toString(): String {
        return "id=$id, title='$title')"
    }
}
