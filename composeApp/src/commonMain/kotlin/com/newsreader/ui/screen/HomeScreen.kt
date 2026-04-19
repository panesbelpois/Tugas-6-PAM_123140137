package com.newsreader.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.newsreader.data.model.Article
import com.newsreader.data.model.UiState
import com.newsreader.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: UiState<List<Article>>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onArticleClick: (Article) -> Unit,
    onSaveToggle: (Int) -> Unit,
    savedIds: Set<Int>
) {
    var selectedCategory by remember { mutableStateOf("Semua") }
    val categories = listOf("Semua", "Tech", "Sport", "Health")

    val pullState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh    = onRefresh,
        state        = pullState,
        modifier     = Modifier.fillMaxSize(),
        indicator    = {
            // Maroon-tinted pull indicator
            PullToRefreshDefaults.Indicator(
                state       = pullState,
                isRefreshing = isRefreshing,
                color        = Primary,
                containerColor = PrimaryContainer,
                modifier     = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {

            // ── HEADER GRADIENT ─────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(Brush.verticalGradient(listOf(PrimaryDark, Primary)))
            ) {
                // Decorative circles
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .offset(x = 220.dp, y = (-60).dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.06f))
                )
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .offset(x = (-20).dp, y = 30.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.05f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp)
                        .padding(top = 20.dp, bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Selamat datang,",
                                color = Color.White.copy(alpha = 0.80f),
                                fontSize = 13.sp
                            )
                            Text(
                                "Anisah Octa R. 👋",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.18f))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🔔", fontSize = 18.sp)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Search bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.15f))
                            .border(1.dp, Color.White.copy(alpha = 0.30f), RoundedCornerShape(50))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔍", fontSize = 15.sp)
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Cari berita terkini...",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // ── CATEGORY CHIPS ───────────────────────────────────────────────
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val selected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (selected) Primary else SurfaceVariant)
                            .border(1.dp, if (selected) PrimaryDark else DividerColor, RoundedCornerShape(50))
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 18.dp, vertical = 9.dp)
                    ) {
                        Text(
                            cat,
                            color = if (selected) Color.White else OnSurface,
                            fontSize = 13.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            // ── UI STATE HANDLER ─────────────────────────────────────────────
            when (val state = uiState) {

                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                color       = Primary,
                                strokeWidth = 3.dp,
                                modifier    = Modifier.size(52.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Memuat berita...",
                                color      = Primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = 15.sp
                            )
                        }
                    }
                }

                is UiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Text("😕", fontSize = 56.sp)
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "Gagal memuat berita",
                                fontWeight = FontWeight.Bold,
                                color      = OnSurface,
                                fontSize   = 18.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                state.message,
                                color    = TextSecondary,
                                fontSize = 13.sp
                            )
                            Spacer(Modifier.height(20.dp))
                            Button(
                                onClick = onRefresh,
                                colors  = ButtonDefaults.buttonColors(containerColor = Primary),
                                shape   = RoundedCornerShape(50)
                            ) {
                                Text(
                                    "Coba Lagi",
                                    color      = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                is UiState.Success -> {
                    val allArticles = state.data
                    val filtered = if (selectedCategory == "Semua") allArticles
                    else allArticles.filter { it.category == selectedCategory }

                    val featured = filtered.firstOrNull()
                    val recents  = if (filtered.size > 1) filtered.drop(1) else emptyList()

                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                "PILIHAN HARI INI",
                                fontSize     = 11.sp,
                                fontWeight   = FontWeight.ExtraBold,
                                color        = Primary,
                                letterSpacing = 1.5.sp,
                                modifier     = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                            )
                        }

                        featured?.let { article ->
                            item {
                                FeaturedArticleCard(
                                    article     = article,
                                    isSaved     = savedIds.contains(article.id),
                                    onSaveToggle = { onSaveToggle(article.id) },
                                    onClick     = { onArticleClick(article) }
                                )
                            }
                        }

                        if (recents.isNotEmpty()) {
                            item {
                                Text(
                                    "BERITA TERKINI",
                                    fontSize     = 11.sp,
                                    fontWeight   = FontWeight.ExtraBold,
                                    color        = Primary,
                                    letterSpacing = 1.5.sp,
                                    modifier     = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                                )
                            }
                            items(recents, key = { it.id }) { article ->
                                ArticleListItem(
                                    article     = article,
                                    isSaved     = savedIds.contains(article.id),
                                    onSaveToggle = { onSaveToggle(article.id) },
                                    onClick     = { onArticleClick(article) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ── Featured Card ─────────────────────────────────────────────────────────────

@Composable
fun FeaturedArticleCard(
    article: Article,
    isSaved: Boolean,
    onSaveToggle: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier  = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(Brush.linearGradient(listOf(GradientTop, GradientBottom)))
            ) {
                // Category badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White.copy(alpha = 0.20f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        article.category.uppercase(),
                        color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold
                    )
                }
                // Save button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.20f))
                        .clickable { onSaveToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (isSaved) "🔖" else "📑", fontSize = 15.sp)
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    article.title,
                    fontWeight = FontWeight.Bold, fontSize = 16.sp,
                    color      = OnSurface, maxLines = 2,
                    overflow   = TextOverflow.Ellipsis, lineHeight = 23.sp
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    article.body,
                    maxLines  = 2, overflow   = TextOverflow.Ellipsis,
                    color     = TextSecondary, fontSize = 13.sp, lineHeight = 18.sp
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(article.timeAgo, color = TextSecondary, fontSize = 12.sp)
                    Button(
                        onClick = onClick,
                        colors  = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape   = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("Baca", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ── Article List Item ─────────────────────────────────────────────────────────

@Composable
fun ArticleListItem(
    article: Article,
    isSaved: Boolean,
    onSaveToggle: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable { onClick() },
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.linearGradient(listOf(PrimaryMid, PrimaryDark))),
                contentAlignment = Alignment.Center
            ) {
                Text("📰", fontSize = 24.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(PrimaryContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        article.category,
                        color = Primary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    article.title,
                    fontWeight = FontWeight.SemiBold, fontSize = 14.sp,
                    color      = OnSurface, maxLines = 2,
                    overflow   = TextOverflow.Ellipsis, lineHeight = 19.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(article.timeAgo, color = TextSecondary, fontSize = 11.sp)
            }

            Spacer(Modifier.width(8.dp))

            IconButton(onClick = onSaveToggle, modifier = Modifier.size(32.dp)) {
                Text(if (isSaved) "🔖" else "📑", fontSize = 16.sp)
            }
        }
    }
}