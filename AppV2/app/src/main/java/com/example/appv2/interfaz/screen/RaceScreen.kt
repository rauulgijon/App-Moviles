package com.example.appv2.interfaz.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.Race
import com.example.appv2.viewmodel.RaceViewModel

@Composable
fun RaceScreen(viewModel: RaceViewModel = viewModel()) {
    val selectedRace = viewModel.selectedRace

    BackHandler(enabled = selectedRace != null) {
        viewModel.onBackToCalendar()
    }

    if (selectedRace != null) {
        RaceDetailScreen(
            race = selectedRace,
            results = viewModel.raceResults,
            isLoading = viewModel.isResultsLoading,
            onBack = { viewModel.onBackToCalendar() }
        )
    } else {
        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        "Calendario 2024",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(viewModel.races) { race ->
                    if (race.winner != null) {
                        PastRaceItem(race) { viewModel.onRaceClicked(race) }
                    } else {
                        UpcomingRaceItem(race)
                    }
                }
            }
        }
    }
}

@Composable
fun PastRaceItem(race: Race, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Cabecera: Bandera + Nombre GP + Circuito
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (race.flagImage != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(race.flagImage).build(),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp).clip(RoundedCornerShape(4.dp))
                    )
                } else {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray)
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(race.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    // AQUI AÑADIMOS EL NOMBRE DEL CIRCUITO
                    Text(race.circuit, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Ganador destacado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFD4AF37)) // Dorado
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("GANADOR", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(race.winner ?: "", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Fecha: ${race.date}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.End))
        }
    }
}

@Composable
fun UpcomingRaceItem(race: Race) {
    Card(elevation = CardDefaults.cardElevation(2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null)
                Text("PRÓX", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(race.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(race.circuit, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text("Fecha: ${race.date}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}