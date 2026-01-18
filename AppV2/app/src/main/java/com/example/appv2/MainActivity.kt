package com.example.appv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
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

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object News : BottomNavItem("news", Icons.Default.Info, "Noticias")
    object Drivers : BottomNavItem("drivers", Icons.Default.Person, "Pilotos")
    object Schedule : BottomNavItem("schedule", Icons.Default.DateRange, "Calendario")
    object Teams : BottomNavItem("teams", Icons.AutoMirrored.Filled.List, "Equipos")
}

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(BottomNavItem.News, BottomNavItem.Drivers, BottomNavItem.Schedule, BottomNavItem.Teams)

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
            when (items[selectedItem]) {
                is BottomNavItem.News -> NewsScreen()
                is BottomNavItem.Drivers -> DriverScreen()
                is BottomNavItem.Schedule -> RaceScreen()
                is BottomNavItem.Teams -> TeamScreen()
            }
        }
    }
}