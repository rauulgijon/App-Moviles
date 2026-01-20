package com.example.appv2.interfaz.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(race.raceName) },
                navigationIcon = {
                    IconButton(onClick = onBack) { // Se usa el parámetro aquí
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (results.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay resultados todavía", color = Color.Gray)
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    item {
                        Row(Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                            Text("Pos", Modifier.width(40.dp), fontWeight = FontWeight.Bold)
                            Text("Piloto", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                            Text("Pts", Modifier.width(40.dp), fontWeight = FontWeight.Bold)
                        }
                        HorizontalDivider()
                    }
                    items(results) { result ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${result.position}", Modifier.width(40.dp), fontWeight = FontWeight.Bold)
                            Text(result.driver?.lastname ?: "---", Modifier.weight(1f))
                            Text("${result.points}", Modifier.width(40.dp), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                    }
                }
            }
        }
    }
}