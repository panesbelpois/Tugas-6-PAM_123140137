package com.newsreader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newsreader.ui.component.BottomNavBar
import com.newsreader.ui.component.BottomNavItem
import com.newsreader.ui.screen.*
import com.newsreader.ui.theme.NewsReaderTheme
import com.newsreader.data.model.Article
import androidx.compose.runtime.saveable.rememberSaveable
import com.newsreader.viewmodel.NewsViewModel

@Composable
fun MainApp() {
    NewsReaderTheme {

        // ── ViewModel ─────────────────────────────────────────────────────────
        val viewModel: NewsViewModel = viewModel { NewsViewModel() }

        // ── Collect StateFlows ────────────────────────────────────────────────
        val articlesState by viewModel.articlesState.collectAsStateWithLifecycle()
        val savedIds      by viewModel.savedIds.collectAsStateWithLifecycle()
        val isRefreshing  by viewModel.isRefreshing.collectAsStateWithLifecycle()

        // ── Navigation state ──────────────────────────────────────────────────
        var currentRoute    by rememberSaveable { mutableStateOf(BottomNavItem.Home.route) }
        var selectedArticle by remember { mutableStateOf<Article?>(null) }

        // ── Detail screen takes over full screen ──────────────────────────────
        if (selectedArticle != null) {
            DetailScreen(
                article     = selectedArticle!!,
                isSaved     = savedIds.contains(selectedArticle!!.id),
                onBack       = { selectedArticle = null },
                onSaveToggle = { viewModel.toggleSave(selectedArticle!!.id) }
            )
            return@NewsReaderTheme
        }

        Scaffold(
            bottomBar = {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate   = { currentRoute = it }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentRoute) {

                    BottomNavItem.Home.route -> HomeScreen(
                        uiState      = articlesState,
                        isRefreshing = isRefreshing,
                        onRefresh    = { viewModel.refresh() },
                        onArticleClick = { selectedArticle = it },
                        onSaveToggle = { viewModel.toggleSave(it) },
                        savedIds     = savedIds
                    )

                    BottomNavItem.Saved.route -> {
                        // Extract article list from state (or empty list if not yet loaded)
                        val articleList = (articlesState as? com.newsreader.data.model.UiState.Success)?.data
                            ?: emptyList()
                        SavedScreen(
                            articles    = articleList,
                            savedIds    = savedIds,
                            onArticleClick = { selectedArticle = it },
                            onSaveToggle = { viewModel.toggleSave(it) },
                            onExplore    = { currentRoute = BottomNavItem.Home.route }
                        )
                    }

                    BottomNavItem.Profile.route -> ProfileScreen(
                        readCount  = 24,
                        savedCount = savedIds.size,
                        activeDays = 7
                    )
                }
            }
        }
    }
}