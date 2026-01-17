package com.example.appv2.interfaz.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onLogout: () -> Unit) {
    // Variable para saber qué pestaña está seleccionada (0=Carreras, 1=Pilotos, 2=Equipos)
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(when(selectedTab) {
                        0 -> "Calendario F1"
                        1 -> "Pilotos"
                        else -> "Equipos"
                    })
                },
                actions = {
                    Button(onClick = onLogout) { Text("Salir") }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.DateRange, contentDescription = "Carreras") },
                    label = { Text("Carreras") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Pilotos") },
                    label = { Text("Pilotos") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Equipos") },
                    label = { Text("Equipos") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { paddingValues ->
        // Aquí mostramos la pantalla correspondiente según la pestaña
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> RaceScreen()
                1 -> DriverScreen()
                2 -> TeamScreen()
            }
        }
    }
}