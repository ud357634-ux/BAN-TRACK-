package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LakshyaGoldLight,
    onPrimary = Color(0xFF451A03),
    primaryContainer = Color(0xFF78350F),
    onPrimaryContainer = LakshyaGoldContainer,
    secondary = Color(0xFF93C5FD),
    onSecondary = Color(0xFF172554),
    secondaryContainer = LakshyaNavySecondary,
    onSecondaryContainer = Color(0xFFDBEAFE),
    tertiary = LakshyaGoldAccent,
    background = LakshyaDarkBackground,
    surface = LakshyaDarkSurface,
    surfaceVariant = LakshyaDarkSurfaceVariant,
    onBackground = Color(0xFFF1F5F9),
    onSurface = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFFCBD5E1),
    error = LakshyaAssamRed,
    outline = Color(0xFF475569)
)

private val LightColorScheme = lightColorScheme(
    primary = LakshyaNavyPrimary,
    onPrimary = Color.White,
    primaryContainer = LakshyaBlueContainer,
    onPrimaryContainer = LakshyaNavyPrimary,
    secondary = LakshyaNavySecondary,
    onSecondary = Color.White,
    secondaryContainer = LakshyaGoldContainer,
    onSecondaryContainer = LakshyaGoldOnContainer,
    tertiary = LakshyaGoldAccent,
    background = LakshyaLightBackground,
    surface = LakshyaLightSurface,
    surfaceVariant = LakshyaLightSurfaceVariant,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A),
    onSurfaceVariant = Color(0xFF475569),
    error = LakshyaAssamRed,
    outline = Color(0xFFCBD5E1)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve Lakshya AI brand theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
