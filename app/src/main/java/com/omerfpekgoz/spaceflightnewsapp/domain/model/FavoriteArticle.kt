package com.omerfpekgoz.spaceflightnewsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
@Entity("FavoriteArticle")
data class FavoriteArticle (
    @PrimaryKey val id: Int,
    val title: String,
    val summary: String,
    val published_at: String,
    val image_url: String,
    val url: String
)