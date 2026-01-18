package com.example.appv2.interfaz.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.Team
import com.example.appv2.viewmodel.TeamViewModel

private const val STORAGE_BASE_URL = "https://rqyytwpfcezjtndbjkwj.supabase.co/storage/v1/object/public/images/BBDD%20F1/"

@Composable
fun TeamScreen(viewModel: TeamViewModel = viewModel()) {
    if (viewModel.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Los equipos ya vienen ordenados por la suma de puntos de sus pilotos desde SQL
            itemsIndexed(viewModel.teams) { index, team ->
                TeamItem(team, index + 1)
            }
        }
    }
}

@Composable
fun TeamItem(team: Team, position: Int) {
    val carUrl = "${STORAGE_BASE_URL}livery/${team.carImage}"
    val logoUrl = "${STORAGE_BASE_URL}equipos/${team.logo}"

    Card(elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary)) {
                        Text("$position", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(team.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Text("${team.points ?: 0} PTS", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(carUrl).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(120.dp),
                contentScale = ContentScale.Fit,
                error = painterResource(android.R.drawable.ic_menu_report_image)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Team Principal", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(team.teamPrincipal ?: "N/A", style = MaterialTheme.typography.bodySmall)
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(logoUrl).build(),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit,
                    error = painterResource(android.R.drawable.ic_menu_report_image)
                )
            }
        }
    }
}