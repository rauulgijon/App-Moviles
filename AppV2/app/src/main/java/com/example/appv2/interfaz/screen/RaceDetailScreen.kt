package com.example.appv2.interfaz.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.Race
import com.example.appv2.model.RaceResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceDetailScreen(
    race: Race,
    results: List<RaceResult>,
    isLoading: Boolean,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(race.name, style = MaterialTheme.typography.titleMedium) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            }
        )

        LazyColumn(contentPadding = PaddingValues(16.dp)) {

            // --- SECCIÓN 1: TRAZADO DEL CIRCUITO (Siempre visible) ---
            item {
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Trazado del Circuito",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        if (race.image != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(race.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Mapa del circuito de ${race.circuit}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp), // Un poco más alto para que se vea bien
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxWidth().height(150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(50.dp), tint = Color.Gray)
                                Text("Mapa no disponible", color = Color.Gray)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("📍 Circuito: ${race.circuit}", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                        Text("📅 Fecha: ${race.date}", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Título de Resultados
                Text(
                    "Resultados de Carrera",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // --- SECCIÓN 2: TABLA DE RESULTADOS ---
            if (isLoading) {
                item { LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) }
            } else if (results.isEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Box(Modifier.padding(16.dp)) {
                            Text("Aún no hay resultados disponibles para esta carrera.", color = Color.Gray)
                        }
                    }
                }
            } else {
                items(results) { result ->
                    ResultRow(result)
                }
            }
        }
    }
}

@Composable
fun ResultRow(result: RaceResult) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        // Si es el 1º, le ponemos un fondo dorado suave
        colors = CardDefaults.cardColors(
            containerColor = if (result.position == 1) Color(0xFFFFD700).copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Posición
                Text(
                    text = "${result.position}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(30.dp),
                    color = if(result.position == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                // Nombre y Equipo
                Column {
                    Text(result.driverName, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                    Text(result.team, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
            // Tiempo
            Text(result.time, style = MaterialTheme.typography.labelMedium)
        }
    }
}