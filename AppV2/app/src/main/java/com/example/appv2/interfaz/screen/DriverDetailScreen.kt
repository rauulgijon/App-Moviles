package com.example.appv2.interfaz.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.Driver

@Composable
fun DriverDetailScreen(driver: Driver, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Volver") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(driver.image).crossfade(true).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp).clip(CircleShape).background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "${driver.firstname} ${driver.lastname}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = driver.country ?: "", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            StatCard("Número", "#${driver.number}")
            StatCard("Puntos", "${driver.points}")
            StatCard("Mundiales", "${driver.worldChampionships}")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Escudería Actual", style = MaterialTheme.typography.labelMedium)
                Text(text = driver.team ?: "Sin contrato", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String) {
    Card(elevation = CardDefaults.cardElevation(2.dp), modifier = Modifier.width(100.dp)) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = MaterialTheme.typography.labelSmall)
            Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}