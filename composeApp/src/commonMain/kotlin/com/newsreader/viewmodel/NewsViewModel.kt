package com.newsreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsreader.data.model.Article
import com.newsreader.data.model.UiState
import com.newsreader.data.remote.HttpClientFactory
import com.newsreader.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository = NewsRepository(HttpClientFactory.create())

    // ── Articles UI State ─────────────────────────────────────────────────────
    private val _articlesState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val articlesState: StateFlow<UiState<List<Article>>> = _articlesState.asStateFlow()

    // ── Saved article IDs ─────────────────────────────────────────────────────
    private val _savedIds = MutableStateFlow<Set<Int>>(emptySet())
    val savedIds: StateFlow<Set<Int>> = _savedIds.asStateFlow()

    // ── Pull-to-refresh indicator ─────────────────────────────────────────────
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadArticles()
    }

    /**
     * Fetches articles from the repository and updates [articlesState].
     * Sets [UiState.Loading] before the call, then [UiState.Success] or [UiState.Error].
     */
    fun loadArticles() {
        viewModelScope.launch {
            _articlesState.update { UiState.Loading }
            repository.getArticles()
                .onSuccess { articles ->
                    _articlesState.update { UiState.Success(articles) }
                }
                .onFailure { error ->
                    _articlesState.update {
                        UiState.Error(error.message ?: "Terjadi kesalahan. Coba lagi.")
                    }
                }
        }
    }

    /**
     * Triggered by swipe-down gesture (Pull-To-Refresh).
     * Shows the refresh spinner, re-fetches data, then hides spinner.
     */
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.update { true }
            repository.getArticles()
                .onSuccess { articles ->
                    _articlesState.update { UiState.Success(articles) }
                }
                .onFailure { error ->
                    _articlesState.update {
                        UiState.Error(error.message ?: "Terjadi kesalahan. Coba lagi.")
                    }
                }
            _isRefreshing.update { false }
        }
    }

    /**
     * Toggles bookmark status for a given article ID.
     */
    fun toggleSave(articleId: Int) {
        _savedIds.update { current ->
            if (current.contains(articleId)) current - articleId
            else current + articleId
        }
    }
}
