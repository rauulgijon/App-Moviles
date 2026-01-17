package com.example.appv2.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),   // Botones pequeños / Chips
    medium = RoundedCornerShape(16.dp), // Tarjetas de pilotos/noticias (Aerodinámico)
    large = RoundedCornerShape(24.dp)   // Ventanas modales / BottomSheet
)