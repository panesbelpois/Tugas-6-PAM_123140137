package com.newsreader.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Catatan: Untuk menggunakan Poppins di KMP, tambahkan font ke:
// composeApp/src/commonMain/composeResources/font/
// File: poppins_regular.ttf, poppins_medium.ttf, poppins_semibold.ttf, poppins_bold.ttf
// Download dari fonts.google.com/specimen/Poppins

// Jika sudah ada font resource, ganti FontFamily.Default dengan:
// val Poppins = FontFamily(
//     Font(Res.font.poppins_regular, FontWeight.Normal),
//     Font(Res.font.poppins_medium, FontWeight.Medium),
//     Font(Res.font.poppins_semibold, FontWeight.SemiBold),
//     Font(Res.font.poppins_bold, FontWeight.Bold),
// )

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)