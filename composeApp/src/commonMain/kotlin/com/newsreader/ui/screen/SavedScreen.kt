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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.newsreader.data.model.Article
import com.newsreader.ui.theme.*

@Composable
fun SavedScreen(
    articles: List<Article>,
    savedIds: Set<Int>,
    onArticleClick: (Article) -> Unit,
    onSaveToggle: (Int) -> Unit,
    onExplore: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Semua") }
    val categories = listOf("Semua", "Tech", "Health", "Sport")

    val savedArticles = articles.filter { savedIds.contains(it.id) }
    val filteredSaved =
        if (selectedCategory == "Semua") savedArticles
        else savedArticles.filter { it.category == selectedCategory }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        // ── HEADER ────────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(PrimaryDark, Primary)))
                .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
        ) {
            // Decorative circle
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = 250.dp, y = (-50).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 24.dp)
            ) {
                Text(
                    "Koleksi kamu",
                    color = Color.White.copy(alpha = 0.80f),
                    fontSize = 13.sp
                )
                Text(
                    "Berita Tersimpan",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${savedArticles.size} artikel tersimpan",
                    color = Color.White.copy(alpha = 0.70f),
                    fontSize = 13.sp
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        // ── FILTER CHIPS ─────────────────────────────────────────────────────
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val active = cat == selectedCategory
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (active) Primary else SurfaceVariant)
                        .border(1.dp, if (active) PrimaryDark else DividerColor, RoundedCornerShape(50))
                        .clickable { selectedCategory = cat }
                        .padding(horizontal = 18.dp, vertical = 9.dp)
                ) {
                    Text(
                        cat,
                        color = if (active) Color.White else OnSurface,
                        fontSize = 13.sp,
                        fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        if (filteredSaved.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📭", fontSize = 56.sp)
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Belum ada artikel tersimpan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = OnSurface
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Simpan berita favoritmu\nbiar gampang dicari lagi",
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = onExplore,
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Jelajahi Berita", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredSaved) { article ->
                    SavedArticleCard(
                        article    = article,
                        onDelete   = { onSaveToggle(article.id) },
                        onClick    = { onArticleClick(article) }
                    )
                }
            }
        }
    }
}

@Composable
fun SavedArticleCard(article: Article, onDelete: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
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
                    Text(article.category, color = Primary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(5.dp))
                Text(
                    article.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = OnSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 19.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(article.timeAgo, fontSize = 11.sp, color = TextSecondary)
            }

            Spacer(Modifier.width(8.dp))

            // Delete button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer)
                    .clickable(onClick = onDelete),
                contentAlignment = Alignment.Center
            ) {
                Text("🗑️", fontSize = 15.sp)
            }
        }
    }
}