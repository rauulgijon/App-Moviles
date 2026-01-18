package com.example.appv2.interfaz.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.Race
import com.example.appv2.viewmodel.RaceViewModel

private const val STORAGE_BASE_URL = "https://rqyytwpfcezjtndbjkwj.supabase.co/storage/v1/object/public/images/BBDD%20F1/"

@Composable
fun RaceScreen(viewModel: RaceViewModel = viewModel()) {
    val selectedRace = viewModel.selectedRace

    if (selectedRace != null) {
        RaceDetailScreen(
            race = selectedRace,
            results = viewModel.raceResults,
            isLoading = viewModel.isResultsLoading,
            onBack = { viewModel.onBackToCalendar() }
        )
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            // TÍTULO DE SECCIÓN
            Text(
                text = "Calendario Oficial 2025",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(16.dp)
            )

            if (viewModel.isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewModel.races) { race ->
                        RaceItem(race = race, onClick = { viewModel.onRaceClicked(race) })
                    }
                }
            }
        }
    }
}

@Composable
fun RaceItem(race: Race, onClick: () -> Unit) {
    val imageUrl = "${STORAGE_BASE_URL}circuitos/${race.circuitImage}"
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(text = "Ronda ${race.round}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                Text(text = race.raceName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = race.date, style = MaterialTheme.typography.bodySmall)
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).build(),
                contentDescription = null,
                modifier = Modifier.size(80.dp).background(Color.White, RoundedCornerShape(8.dp)).padding(4.dp),
                contentScale = ContentScale.Fit,
                error = painterResource(android.R.drawable.ic_menu_report_image)
            )
        }
    }
}