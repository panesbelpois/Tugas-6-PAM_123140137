package com.newsreader.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val RedVelvetColorScheme = lightColorScheme(
    primary            = Primary,
    onPrimary          = OnPrimary,
    primaryContainer   = PrimaryContainer,
    secondary          = Secondary,
    background         = Background,
    surface            = Surface,
    surfaceVariant     = SurfaceVariant,
    onBackground       = OnBackground,
    onSurface          = OnSurface,
)

@Composable
fun NewsReaderTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = RedVelvetColorScheme,
        typography  = AppTypography,
        content     = content
    )
}