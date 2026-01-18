package com.example.appv2.interfaz.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import com.example.appv2.model.Driver
import com.example.appv2.viewmodel.DriverViewModel

private const val STORAGE_BASE_URL = "https://rqyytwpfcezjtndbjkwj.supabase.co/storage/v1/object/public/images/BBDD%20F1/"

@Composable
fun DriverScreen(viewModel: DriverViewModel = viewModel()) {
    val selectedDriver = viewModel.selectedDriver
    if (selectedDriver != null) {
        DriverDetailScreen(driver = selectedDriver, onBack = { viewModel.onDriverSelected(null) })
    } else {
        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Los pilotos ya vienen ordenados por puntos desde la Vista SQL
                itemsIndexed(viewModel.drivers) { index, driver ->
                    DriverItem(driver = driver, position = index + 1, onClick = { viewModel.onDriverSelected(driver) })
                }
            }
        }
    }
}

@Composable
fun DriverItem(driver: Driver, position: Int, onClick: () -> Unit) {
    val imageUrl = "${STORAGE_BASE_URL}pilotos/${driver.image}"

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary)
                ) {
                    Text("$position", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(12.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build(),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.LightGray),
                    error = painterResource(android.R.drawable.ic_menu_report_image)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(driver.lastname, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(driver.team ?: "N/A", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
            Text(
                text = "${driver.points ?: 0} PTS",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}