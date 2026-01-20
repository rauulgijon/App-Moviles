package com.example.appv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.appv2.interfaz.screen.*
import com.example.appv2.ui.theme.AppV2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Definimos el layout Compose de la activity
        setContent { AppV2Theme { MainScreen() } }
    }
}
// Clase para gestionar los elementos del menú
sealed class BottomNavItem(val route: String, val icon: ImageVector, val labelRes: Int) {
    object News : BottomNavItem("news", Icons.Default.Info, R.string.menu_news)
    object Drivers : BottomNavItem("drivers", Icons.Default.Person, R.string.menu_drivers)
    object Schedule : BottomNavItem("schedule", Icons.Default.DateRange, R.string.menu_races)
    object Teams : BottomNavItem("teams", Icons.AutoMirrored.Filled.List, R.string.menu_teams)
    object Profile : BottomNavItem("profile", Icons.Default.AccountCircle, R.string.menu_profile)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Estado para controlar el elemento seleccionado en el menú
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        BottomNavItem.News,
        BottomNavItem.Drivers,
        BottomNavItem.Schedule,
        BottomNavItem.Teams,
        BottomNavItem.Profile
    )
    // Scaffold para estructura (TopBar, Content, BottomBar)
    Scaffold(
        topBar = {
            // Logo centrado arriba
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logof1), // Asegúrate de tener logo_splash en drawable
                        contentDescription = "F1 Logo",
                        modifier = Modifier.height(32.dp)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            // Barra de navegacion abajjo
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(stringResource(item.labelRes)) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        // Content
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