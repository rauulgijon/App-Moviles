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
import androidx.compose.ui.res.stringResource // IMPORTANTE
import androidx.compose.ui.text.style.TextOverflow
import com.example.appv2.R // IMPORTANTE: Asegúrate de importar tu R
import com.example.appv2.interfaz.AboutActivity
import com.example.appv2.interfaz.screen.NewsScreen
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
                        // Usamos stringResource para traducir el título
                        text = when(selectedTab) {
                            0 -> stringResource(R.string.race_title) // Calendario 2024
                            1 -> stringResource(R.string.menu_drivers) // Pilotos
                            2 -> stringResource(R.string.menu_teams)   // Equipos
                            3 -> stringResource(R.string.news_title)   // F1 News
                            else -> stringResource(R.string.menu_profile) // Perfil
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) { Icon(Icons.Default.MoreVert, "Menu") }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.menu_about)) }, // Acerca de
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
                    label = { Text(stringResource(R.string.menu_races)) }
                )
                // 2. PILOTOS
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text(stringResource(R.string.menu_drivers)) }
                )
                // 3. EQUIPOS
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text(stringResource(R.string.menu_teams)) }
                )
                // 4. NOTICIAS
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.List, null) },
                    label = { Text(stringResource(R.string.menu_news)) }
                )
                // 5. PERFIL
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = { Icon(Icons.Default.AccountCircle, null) },
                    label = { Text(stringResource(R.string.menu_profile)) }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> RaceScreen()
                1 -> DriverScreen()
                2 -> TeamScreen()
                3 -> NewsScreen(onBack = null)
                4 -> ProfileScreen()
            }
        }
    }
}