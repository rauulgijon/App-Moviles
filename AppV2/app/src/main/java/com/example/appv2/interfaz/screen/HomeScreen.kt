package com.example.appv2.interfaz.screen

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.example.appv2.interfaz.AboutActivity
import com.example.appv2.interfaz.NewsScreen // Importamos la pantalla de noticias
import com.example.appv2.interfaz.SettingsActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // 0: Carreras, 1: Pilotos, 2: Equipos, 3: Noticias, 4: Perfil
    var selectedTab by remember { mutableStateOf(0) }
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when(selectedTab) {
                            0 -> "Calendario 2024"
                            1 -> "Pilotos"
                            2 -> "Constructores"
                            3 -> "Últimas Noticias"
                            else -> "Mi Perfil"
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) { Icon(Icons.Default.MoreVert, "Menu") }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        // El menú superior queda solo para "Acerca de", ya que ajustes está en perfil
                        DropdownMenuItem(
                            text = { Text("Acerca de") },
                            onClick = { showMenu = false; context.startActivity(Intent(context, AboutActivity::class.java)) }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                // 1. CARRERAS
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.DateRange, null) },
                    label = { Text("Carreras") }
                )
                // 2. PILOTOS
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Pilotos") }
                )
                // 3. EQUIPOS
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Home, null) }, // O Icons.Default.Build
                    label = { Text("Equipos") }
                )
                // 4. NOTICIAS (Nuevo)
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.List, null) }, // O Icons.Default.Email
                    label = { Text("Noticias") }
                )
                // 5. PERFIL (Nuevo)
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = { Icon(Icons.Default.AccountCircle, null) },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> RaceScreen()
                1 -> DriverScreen()
                2 -> TeamScreen()
                3 -> NewsScreen(onBack = null) // Llamamos a Noticias sin botón de volver
                4 -> ProfileScreen()           // Llamamos a Perfil
            }
        }
    }
}