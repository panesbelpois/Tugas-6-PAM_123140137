package com.newsreader.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newsreader.data.model.Article
import com.newsreader.ui.theme.*

@Composable
fun DetailScreen(
    article: Article,
    isSaved: Boolean,
    onBack: () -> Unit,
    onSaveToggle: () -> Unit
) {
    var localSaved by remember { mutableStateOf(isSaved) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {

        // ── TOP HEADER GRADIENT ───────────────────────────────────────────────
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
                    .offset(x = 240.dp, y = (-50).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .padding(top = 14.dp, bottom = 20.dp)
            ) {
                // Back + Save row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable(onClick = onBack)
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.20f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("←", color = Color.White, fontSize = 18.sp)
                        }
                        Spacer(Modifier.width(8.dp))
                        Text("Kembali", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.20f))
                                .clickable {
                                    localSaved = !localSaved
                                    onSaveToggle()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(if (localSaved) "🔖" else "📑", fontSize = 16.sp)
                        }
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.20f))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("⋯", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Article title in header
                Text(
                    article.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = 26.sp
                )

                Spacer(Modifier.height(8.dp))
            }
        }

        // ── SCROLLABLE BODY ───────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            // Category + meta row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PrimaryContainer)
                        .border(1.dp, Primary.copy(alpha = 0.30f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        article.category.uppercase(),
                        color = Primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
                Text("38% terbaca", color = TextSecondary, fontSize = 12.sp)
            }

            Spacer(Modifier.height(12.dp))

            // Meta info
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("⏱ ${article.timeAgo}", color = TextSecondary, fontSize = 12.sp)
                Text("📖 ${article.readTime}", color = TextSecondary, fontSize = 12.sp)
                Text("👁 ${article.views}", color = TextSecondary, fontSize = 12.sp)
            }

            Spacer(Modifier.height(16.dp))

            // Gradient image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.linearGradient(listOf(GradientTop, GradientBottom))),
                contentAlignment = Alignment.Center
            ) {
                Text("📰", fontSize = 52.sp)
            }

            Spacer(Modifier.height(16.dp))

            // Author card
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(PrimaryMid, PrimaryDark))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("RD", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Rizky Darmawan", fontWeight = FontWeight.SemiBold, color = OnSurface, fontSize = 14.sp)
                            Text("Tech Journalist", color = TextSecondary, fontSize = 12.sp)
                        }
                    }

                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(50),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Primary),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("+ Ikuti", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            HorizontalDivider(color = DividerColor, thickness = 1.dp)

            Spacer(Modifier.height(16.dp))

            // Body text
            Text(
                article.body,
                color = OnSurface,
                fontSize = 15.sp,
                lineHeight = 25.sp
            )

            Spacer(Modifier.height(20.dp))

            // Quote card
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryContainer),
                modifier = Modifier.border(1.dp, Primary.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(48.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Primary)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "\"Teknologi bukan sekadar alat — ia adalah masa depan.\"",
                        fontStyle = FontStyle.Italic,
                        color = Primary,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        // ── BOTTOM ACTION BAR ─────────────────────────────────────────────────
        Surface(
            color = Surface,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❤️", fontSize = 20.sp)
                        Text("248", color = TextSecondary, fontSize = 11.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("💬", fontSize = 20.sp)
                        Text("32", color = TextSecondary, fontSize = 11.sp)
                    }
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text("Bagikan", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}