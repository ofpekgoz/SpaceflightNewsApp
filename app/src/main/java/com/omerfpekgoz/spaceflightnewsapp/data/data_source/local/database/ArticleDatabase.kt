package com.omerfpekgoz.spaceflightnewsapp.data.data_source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omerfpekgoz.spaceflightnewsapp.data.data_source.local.dao.ArticleDao
import com.omerfpekgoz.spaceflightnewsapp.domain.model.ArticleEntity
import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
@Database(entities = [ArticleEntity::class, FavoriteArticle::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao

    companion object {
        const val DATABASE_NAME = "spaceflight_db"
    }
}