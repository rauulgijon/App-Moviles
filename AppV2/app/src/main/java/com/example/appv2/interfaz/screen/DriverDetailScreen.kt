package com.example.appv2.interfaz.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.Driver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverDetailScreen(driver: Driver, onBack: () -> Unit) {
    val storageUrl = "https://rqyytwpfcezjtndbjkwj.supabase.co/storage/v1/object/public/images/BBDD%20F1/pilotos/"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${driver.firstname} ${driver.lastname}") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") }
                }
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${storageUrl}${driver.image}")
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop, // CORRECCIÓN: Foto ajustada
                modifier = Modifier.size(220.dp).clip(CircleShape).background(Color.LightGray),
                error = painterResource(android.R.drawable.ic_menu_report_image)
            )
            Spacer(Modifier.height(24.dp))
            Text(text = driver.team ?: "Sin equipo", style = MaterialTheme.typography.headlineSmall, color = Color.Gray)
            Text(text = "${driver.points ?: 0} PTS", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Black)
        }
    }
}