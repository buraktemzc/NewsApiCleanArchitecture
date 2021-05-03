package com.ebt.newsapicleanarchitecture.presentation.di

import com.ebt.newsapicleanarchitecture.data.repository.ArticleRepositoryImpl
import com.ebt.newsapicleanarchitecture.data.repository.datasource.ArticleRemoteDataSource
import com.ebt.newsapicleanarchitecture.domain.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideArticleRepository(articleRemoteDataSource: ArticleRemoteDataSource): ArticleRepository {
        return ArticleRepositoryImpl(articleRemoteDataSource)
    }
}