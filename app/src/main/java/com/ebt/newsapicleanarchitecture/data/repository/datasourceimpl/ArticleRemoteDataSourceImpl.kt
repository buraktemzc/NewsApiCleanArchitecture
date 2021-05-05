package com.ebt.newsapicleanarchitecture.data.repository.datasourceimpl

import com.ebt.newsapicleanarchitecture.data.api.ApiService
import com.ebt.newsapicleanarchitecture.data.model.APIResponse
import com.ebt.newsapicleanarchitecture.data.repository.datasource.ArticleRemoteDataSource
import retrofit2.Response

class ArticleRemoteDataSourceImpl(private val apiService: ApiService) : ArticleRemoteDataSource {
    override suspend fun getArticles(
        page: Int?,
        sortBy: String,
        query: String,
        fromDate: String?,
        toDate: String?
    ): Response<APIResponse> {
        return apiService.getTopHeadlines(page, sortBy, query, fromDate, toDate)
    }
}