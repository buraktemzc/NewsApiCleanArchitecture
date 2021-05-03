package com.ebt.newsapicleanarchitecture.domain.repository


import com.ebt.newsapicleanarchitecture.data.model.APIResponse
import com.ebt.newsapicleanarchitecture.data.util.Result

interface ArticleRepository {
    suspend fun getArticles(
        page: Int,
        sortBy: String,
        query: String,
        fromDate: String,
        toDate: String
    ): Result<APIResponse>
}