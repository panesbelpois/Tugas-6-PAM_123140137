package com.newsreader.data.remote

import com.newsreader.data.model.ApiPost
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }
}

class NewsApi(private val client: HttpClient) {
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    suspend fun getPosts(): List<ApiPost> {
        return client.get("$baseUrl/posts").body()
    }

    suspend fun getPostById(id: Int): ApiPost {
        return client.get("$baseUrl/posts/$id").body()
    }
}