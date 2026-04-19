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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newsreader.ui.theme.*
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.Image
import anisahpam6.composeapp.generated.resources.Res
import anisahpam6.composeapp.generated.resources.profile

@Composable
fun ProfileScreen(
    readCount: Int = 24,
    savedCount: Int = 2,
    activeDays: Int = 7
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
    ) {

        // ── HEADER BANNER ─────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(PrimaryDark, Primary)))
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
        ) {
            // Decorative circle
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = 240.dp, y = (-80).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f))
            )
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = (-30).dp, y = 40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.05f))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ── PROFILE PHOTO ──
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color.White.copy(alpha = 0.60f), CircleShape)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.profile),
                        contentDescription = "Foto Profil Anisah",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    "Anisah Octa Rohila",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "NIM · 123140137",
                    color = Color.White.copy(alpha = 0.78f),
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(20.dp))

                // Stats row inside header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    HeaderStatItem(readCount.toString(),    "Dibaca")
                    Box(Modifier.width(1.dp).height(36.dp).background(Color.White.copy(alpha = 0.30f)))
                    HeaderStatItem(savedCount.toString(),   "Tersimpan")
                    Box(Modifier.width(1.dp).height(36.dp).background(Color.White.copy(alpha = 0.30f)))
                    HeaderStatItem(activeDays.toString(),   "Hari aktif")
                }

                Spacer(Modifier.height(24.dp))
            }
        }

        Spacer(Modifier.height(20.dp))

        // ── MENU CARD ─────────────────────────────────────────────────────────
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column {
                ProfileMenuItem("👤", "Profil Saya",        "Edit informasi akun",   showDivider = true)
                ProfileMenuItem("🔔", "Notifikasi",          "Atur preferensi",       showDivider = true)
                ProfileMenuItem("⚙️", "Preferensi Topik",   "Pilih topik favorit",   showDivider = true)
                ProfileMenuItem("ℹ️", "Tentang Aplikasi",  "Versi 1.0.0",           showDivider = false)
            }
        }

        Spacer(Modifier.height(16.dp))

        // ── LOGOUT BUTTON ─────────────────────────────────────────────────────
        OutlinedButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Primary),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = PrimaryContainer,
                contentColor   = Primary
            )
        ) {
            Text("Keluar", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Primary)
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun HeaderStatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            color      = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize   = 22.sp
        )
        Spacer(Modifier.height(2.dp))
        Text(
            label,
            color    = Color.White.copy(alpha = 0.78f),
            fontSize = 11.sp
        )
    }
}

@Composable
private fun ProfileMenuItem(
    icon: String,
    title: String,
    subtitle: String,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 20.sp)
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title,    fontWeight = FontWeight.SemiBold, color = OnSurface, fontSize = 14.sp)
                Text(subtitle, color = TextSecondary, fontSize = 12.sp)
            }

            Text("›", color = TextSecondary, fontSize = 22.sp, fontWeight = FontWeight.Light)
        }

        if (showDivider) {
            HorizontalDivider(
                color = DividerColor,
                thickness = 0.8.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}