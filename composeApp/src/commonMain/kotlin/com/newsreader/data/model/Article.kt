package com.newsreader.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int = 0,
    val userId: Int = 0,
    val title: String = "",
    val body: String = "",
    // Field tambahan untuk UI
    val category: String = "Tech",
    val timeAgo: String = "2 jam lalu",
    val readTime: String = "4 min baca",
    val views: String = "1.2k",
    val isSaved: Boolean = false
)

@Serializable
data class ApiPost(
    val id: Int,
    @SerialName("userId") val userId: Int,
    val title: String,
    val body: String
)

fun ApiPost.toArticle(): Article {
    val categories = listOf("Tech", "Sport", "Health")
    return Article(
        id = this.id,
        userId = this.userId,
        title = this.title.replaceFirstChar { it.uppercase() },
        body = this.body,
        category = categories[this.id % categories.size],
        timeAgo = "${(this.id % 12) + 1} jam lalu",
        readTime = "${(this.id % 5) + 2} min baca",
        views = "${(this.id * 13) % 100}.${this.id % 9}k"
    )
}