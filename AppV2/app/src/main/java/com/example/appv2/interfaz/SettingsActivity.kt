package com.example.appv2.interfaz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appv2.data.UserPreferences
import com.example.appv2.ui.theme.AppV2Theme
import kotlinx.coroutines.launch

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userPreferences = UserPreferences(this)

        setContent {
            AppV2Theme {
                SettingsScreen(userPreferences) { finish() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(userPreferences: UserPreferences, onBack: () -> Unit) {
    val notif by userPreferences.getNotifications.collectAsState(initial = true)
    val dark by userPreferences.getDarkMode.collectAsState(initial = false)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Text("Notificaciones", style = MaterialTheme.typography.bodyLarge)
                Switch(checked = notif, onCheckedChange = { scope.launch { userPreferences.saveNotifications(it) } })
            }

            Divider(Modifier.padding(vertical = 16.dp))

            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                Text("Forzar Modo Oscuro", style = MaterialTheme.typography.bodyLarge)
                Switch(checked = dark, onCheckedChange = { scope.launch { userPreferences.saveDarkMode(it) } })
            }
        }
    }
}