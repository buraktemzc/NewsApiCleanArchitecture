package com.ebt.newsapicleanarchitecture.data.api

import com.ebt.newsapicleanarchitecture.BuildConfig
import com.ebt.newsapicleanarchitecture.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything")
    suspend fun getTopHeadlines(
        @Query("page")
        page: Int?,
        @Query("sortBy")
        sortBy: String?,
        @Query("q")
        query: String?,
        @Query("from")
        fromDate: String?,
        @Query("to")
        toDate: String?,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>
}