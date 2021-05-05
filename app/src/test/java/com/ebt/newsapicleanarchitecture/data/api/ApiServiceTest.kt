package com.ebt.newsapicleanarchitecture.data.api

import com.ebt.newsapicleanarchitecture.BuildConfig
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {
    //https://newsapi.org/v2/everything?q=football&sortBy=publishedAt&from=2021-04-19&to=2021-04-20&apiKey=${BuildConfig.API_KEY}
    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun `Get articles from API and received data not null`() {
        runBlocking {
            enqueueMockResponse("everything_response.json")
            val responseBody = service.getArticles(
                null,
                query = "football",
                sortBy = "publishedAt",
                fromDate = "2021-04-19",
                toDate = "2021-04-20"
            ).body()
            mockWebServer.takeRequest()
            assertThat(responseBody).isNotNull()
        }
    }

    @Test
    fun `Get articles from API and request path is equal as expected`() {
        runBlocking {
            enqueueMockResponse("everything_response.json")
            val responseBody = service.getArticles(
                null,
                query = "football",
                sortBy = "publishedAt",
                fromDate = "2021-04-19",
                toDate = "2021-04-20"
            ).body()
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/v2/everything?sortBy=publishedAt&q=football&from=2021-04-19&to=2021-04-20&apiKey=${BuildConfig.API_KEY}")
        }
    }

    @Test
    fun `Get articles from API and received response has correct data size`() {
        runBlocking {
            enqueueMockResponse("everything_response.json")
            val responseBody = service.getArticles(
                null,
                query = "football",
                sortBy = "publishedAt",
                fromDate = "2021-04-19",
                toDate = "2021-04-20"
            ).body()
            val articleList = responseBody!!.articles
            assertThat(articleList?.size ?: 0).isEqualTo(20)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueMockResponse(jsonFileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(jsonFileName)
        val resource = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(resource.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }
}