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

private const val STORAGE_BASE_URL = "https://rqyytwpfcezjtndbjkwj.supabase.co/storage/v1/object/public/images/BBDD%20F1/"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverDetailScreen(driver: Driver, onBack: () -> Unit) {
    val imageUrl = "${STORAGE_BASE_URL}pilotos/${driver.image}"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${driver.firstname} ${driver.lastname}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).build(),
                contentDescription = null,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clip(CircleShape).background(Color.LightGray),
                error = painterResource(android.R.drawable.ic_menu_report_image)
            )
            Spacer(Modifier.height(20.dp))
            Text(driver.team ?: "Sin Equipo", style = MaterialTheme.typography.headlineSmall, color = Color.Gray)
            Text("${driver.points} Puntos", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Black)
        }
    }
}