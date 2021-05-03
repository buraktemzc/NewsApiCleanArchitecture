package com.ebt.newsapicleanarchitecture.presentation.di

import com.ebt.newsapicleanarchitecture.domain.repository.ArticleRepository
import com.ebt.newsapicleanarchitecture.domain.usecase.GetArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetArticlesUseCase(articleRepository: ArticleRepository): GetArticlesUseCase {
        return GetArticlesUseCase(articleRepository)
    }
}