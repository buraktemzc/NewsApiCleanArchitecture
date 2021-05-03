package com.ebt.newsapicleanarchitecture.data.repository

import com.ebt.newsapicleanarchitecture.data.model.APIResponse
import com.ebt.newsapicleanarchitecture.data.repository.datasource.ArticleRemoteDataSource
import com.ebt.newsapicleanarchitecture.data.util.Result
import com.ebt.newsapicleanarchitecture.domain.repository.ArticleRepository
import retrofit2.Response
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override suspend fun getArticles(
        page: Int,
        sortBy: String,
        query: String,
        fromDate: String,
        toDate: String
    ): Result<APIResponse> {
        return convertToResult(
            articleRemoteDataSource.getArticles(
                page,
                sortBy,
                query,
                fromDate,
                toDate
            )
        )
    }

    private fun convertToResult(response: Response<APIResponse>): Result<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.Success(it)
            }
        }
        return Result.Error(response.message())
    }
}