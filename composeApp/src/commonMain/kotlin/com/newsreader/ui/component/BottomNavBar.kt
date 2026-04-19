package com.newsreader.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newsreader.ui.theme.Primary
import com.newsreader.ui.theme.PrimaryContainer
import com.newsreader.ui.theme.TextSecondary

sealed class BottomNavItem(val route: String, val label: String, val icon: String) {
    object Home    : BottomNavItem("home",    "Home",  "🏠")
    object Saved   : BottomNavItem("saved",   "Saved", "🔖")
    object Profile : BottomNavItem("profile", "Profil","👤")
}

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Saved,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick  = { onNavigate(item.route) },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (selected) PrimaryContainer else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text  = item.icon,
                            fontSize = 18.sp
                        )
                    }
                },
                label = {
                    Text(
                        text       = item.label,
                        fontSize   = 10.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color      = if (selected) Primary else TextSecondary
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = Primary,
                    selectedTextColor   = Primary,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                    indicatorColor      = Color.Transparent
                )
            )
        }
    }
}