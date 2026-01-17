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
import kotlinx.coroutines.launch

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userPreferences = UserPreferences(this)
        setContent {
            MaterialTheme {
                SettingsScreen(userPreferences) { finish() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // <--- ESTO ES IMPORTANTE
@Composable
fun SettingsScreen(userPreferences: UserPreferences, onBack: () -> Unit) {
    // Leemos los valores del DataStore como estado
    val notif by userPreferences.getNotifications.collectAsState(initial = true)
    val dark by userPreferences.getDarkMode.collectAsState(initial = false)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Switch Notificaciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recibir notificaciones")
                Switch(
                    checked = notif,
                    onCheckedChange = { isChecked ->
                        scope.launch { userPreferences.saveNotifications(isChecked) }
                    }
                )
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Switch Modo Oscuro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Modo Oscuro")
                Switch(
                    checked = dark,
                    onCheckedChange = { isChecked ->
                        scope.launch { userPreferences.saveDarkMode(isChecked) }
                    }
                )
            }
        }
    }
}