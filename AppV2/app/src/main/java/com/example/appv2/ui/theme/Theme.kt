package com.example.appv2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Esquema Oscuro
private val DarkColorScheme = darkColorScheme(
    primary = F1Red,
    onPrimary = White,
    secondary = F1RedDark,
    onSecondary = White,
    tertiary = F1Red,
    background = DarkGray,
    surface = Color(0xFF2C2C2C),
    onSurface = TextWhite
)

// Esquema Claro
private val LightColorScheme = lightColorScheme(
    primary = F1Red,
    onPrimary = White,
    secondary = F1RedDark,
    onSecondary = White,
    tertiary = F1Red,
    background = LightGray,
    surface = White,
    onSurface = TextBlack
)

@Composable
fun AppV2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Desactivado para mantener identidad F1
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Barra de estado roja
            window.statusBarColor = F1Red.toArgb()
            // Controladores de insets para iconos claros/oscuros
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}