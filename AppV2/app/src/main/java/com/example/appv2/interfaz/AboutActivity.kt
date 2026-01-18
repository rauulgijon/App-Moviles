package com.example.appv2.interfaz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource // IMPORTANTE
import androidx.compose.ui.unit.dp
import com.example.appv2.R // IMPORTANTE
import com.example.appv2.ui.theme.AppV2Theme

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppV2Theme {
                AboutScreen { finish() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.about_title)) }, // Acerca de
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, stringResource(R.string.btn_back)) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Default.Info, null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(24.dp))
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineMedium)
            Text(stringResource(R.string.about_version), style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(48.dp))
            Text(stringResource(R.string.about_developer), style = MaterialTheme.typography.labelLarge)
            Text("Raúl Gijón", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}