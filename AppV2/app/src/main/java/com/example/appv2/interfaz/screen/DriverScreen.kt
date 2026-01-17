package com.example.appv2.interfaz.screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.Driver
import com.example.appv2.viewmodel.DriverViewModel

@Composable
fun DriverScreen(viewModel: DriverViewModel = viewModel()) {
    val selectedDriver = viewModel.selectedDriver

    BackHandler(enabled = selectedDriver != null) {
        viewModel.onBackToGallery()
    }

    if (selectedDriver != null) {
        DriverDetailScreen(
            driver = selectedDriver,
            onBack = { viewModel.onBackToGallery() }
        )
    } else {
        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(viewModel.drivers) { index, driver ->
                    DriverItem(driver, index + 1) { viewModel.onDriverClicked(driver) }
                }
            }
        }
    }
}

@Composable
fun DriverItem(driver: Driver, position: Int, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$position", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier = Modifier.width(30.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(driver.image).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "${driver.firstname} ${driver.lastname}", style = MaterialTheme.typography.titleMedium)
                Text(text = driver.team ?: "Sin equipo", style = MaterialTheme.typography.bodyMedium)
                Text(text = "${driver.points} PTS", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}