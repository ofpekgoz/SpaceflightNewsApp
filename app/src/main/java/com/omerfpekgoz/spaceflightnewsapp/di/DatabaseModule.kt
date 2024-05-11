package com.omerfpekgoz.spaceflightnewsapp.di

import android.app.Application
import androidx.room.Room
import com.omerfpekgoz.spaceflightnewsapp.data.data_source.local.dao.ArticleDao
import com.omerfpekgoz.spaceflightnewsapp.data.data_source.local.database.ArticleDatabase
import com.omerfpekgoz.spaceflightnewsapp.data.data_source.local.database.ArticleDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideArticleDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(
            app,
            ArticleDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun providesArticleDAO(
        articleDatabase: ArticleDatabase
    ): ArticleDao =
        articleDatabase.articleDao
}