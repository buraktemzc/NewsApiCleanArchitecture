package com.ebt.newsapicleanarchitecture.domain.usecase

import com.ebt.newsapicleanarchitecture.data.model.APIResponse
import com.ebt.newsapicleanarchitecture.data.util.Result
import com.ebt.newsapicleanarchitecture.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(private val articleRepository: ArticleRepository) {
    suspend fun execute(
        page: Int,
        sortBy: String,
        query: String,
        fromDate: String?,
        toDate: String?
    ): Result<APIResponse> {
        return articleRepository.getArticles(page, sortBy, query, fromDate, toDate)
    }
}