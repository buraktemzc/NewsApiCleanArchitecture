package com.ebt.newsapicleanarchitecture.data.repository.datasource

import com.ebt.newsapicleanarchitecture.data.model.APIResponse
import retrofit2.Response

interface ArticleRemoteDataSource {
    suspend fun getArticles(
        page: Int?,
        sortBy: String,
        query: String,
        fromDate: String?,
        toDate: String?
    ): Response<APIResponse>
}