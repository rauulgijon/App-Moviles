package com.example.appv2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Definimos la paleta OSCURA (La que usaremos siempre para el look F1)
private val DarkColorScheme = darkColorScheme(
    primary = RacingRed,
    onPrimary = PureWhite,
    primaryContainer = RacingRedDark,
    onPrimaryContainer = PureWhite,
    secondary = MetalGrey,
    onSecondary = PureWhite,
    background = CarbonBlack,     // <--- Fondo NEGRO
    onBackground = PureWhite,     // <--- Texto BLANCO
    surface = AsphaltGrey,        // <--- Tarjetas GRISES
    onSurface = PureWhite,
    surfaceVariant = MetalGrey,
    onSurfaceVariant = SilverText
)

// La paleta clara la dejamos igual que la oscura para "forzar" el estilo racing
// o la definimos muy parecida.
private val LightColorScheme = DarkColorScheme

@Composable
fun AppV2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Desactivamos el color dinámico para que NO coja el color del fondo de pantalla del usuario
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // <--- FORZAMOS SIEMPRE EL TEMA OSCURO

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Asegúrate de tener Type.kt también
        content = content
    )
}