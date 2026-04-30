package com.newsreader.data.repository

import com.newsreader.data.model.Article
import com.newsreader.data.model.toArticle
import com.newsreader.data.remote.NewsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import io.ktor.client.HttpClient

class NewsRepository(private val client: HttpClient) {
    private val api = NewsApi(client)
    private val settings = Settings()
    private val savedArticles = mutableSetOf<Int>()

    init {
        val savedString = settings.getString("saved_articles", "")
        if (savedString.isNotEmpty()) {
            savedArticles.addAll(savedString.split(",").mapNotNull { it.toIntOrNull() })
        }
    }

    suspend fun getArticles(): Result<List<Article>> {
        return try {
            val posts = api.getPosts()
            val articles = posts.take(20).map { it.toArticle() }
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            val post = api.getPostById(id)
            Result.success(post.toArticle())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun toggleSave(articleId: Int): Boolean {
        val result = if (savedArticles.contains(articleId)) {
            savedArticles.remove(articleId)
            false
        } else {
            savedArticles.add(articleId)
            true
        }
        settings.putString("saved_articles", savedArticles.joinToString(","))
        return result
    }

    fun getSavedIds(): Set<Int> = savedArticles.toSet()
}