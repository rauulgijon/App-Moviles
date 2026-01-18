package com.example.appv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appv2.interfaz.screen.DriverScreen
import com.example.appv2.interfaz.screen.NewsScreen
import com.example.appv2.interfaz.screen.RaceScreen
import com.example.appv2.interfaz.screen.TeamScreen
import com.example.appv2.interfaz.screen.ProfileScreen
import com.example.appv2.ui.theme.AppV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppV2Theme {
                MainScreen()
            }
        }
    }
}

// DEFINICIÓN CORREGIDA DE LA SEALED CLASS
sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object News : BottomNavItem("news", Icons.Default.Info, "Noticias")
    object Drivers : BottomNavItem("drivers", Icons.Default.Person, "Pilotos")
    object Schedule : BottomNavItem("schedule", Icons.Default.DateRange, "Calendario")
    object Teams : BottomNavItem("teams", Icons.AutoMirrored.Filled.List, "Equipos")
    object Profile : BottomNavItem("profile", Icons.Default.AccountCircle, "Perfil") // <-- AÑADIDO AQUÍ
}

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }

    // LISTA DE ITEMS USANDO LOS OBJETOS DE LA CLASE SELLADA
    val items = listOf(
        BottomNavItem.News,
        BottomNavItem.Drivers,
        BottomNavItem.Schedule,
        BottomNavItem.Teams,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (selectedItem) {
                0 -> NewsScreen()
                1 -> DriverScreen()
                2 -> RaceScreen()
                3 -> TeamScreen()
                4 -> ProfileScreen()
            }
        }
    }
}