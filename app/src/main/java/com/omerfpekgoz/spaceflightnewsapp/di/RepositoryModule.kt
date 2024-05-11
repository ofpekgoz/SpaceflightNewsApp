package com.omerfpekgoz.spaceflightnewsapp.di

import com.omerfpekgoz.spaceflightnewsapp.data.repository.ArticleRepositoryImpl
import com.omerfpekgoz.spaceflightnewsapp.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindArticleRepository(
        repositoryImpl: ArticleRepositoryImpl
    ): ArticleRepository
}