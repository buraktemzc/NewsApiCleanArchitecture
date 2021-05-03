package com.ebt.newsapicleanarchitecture.presentation.di

import com.ebt.newsapicleanarchitecture.data.api.ApiService
import com.ebt.newsapicleanarchitecture.data.repository.datasource.ArticleRemoteDataSource
import com.ebt.newsapicleanarchitecture.data.repository.datasourceimpl.ArticleRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideArticleRemoteDataSource(
        apiService: ApiService
    ): ArticleRemoteDataSource {
        return ArticleRemoteDataSourceImpl(apiService)
    }
}