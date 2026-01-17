package com.example.appv2.interfaz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.ui.theme.AppV2Theme

class NewsDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("EXTRA_TITLE") ?: "Noticia"
        val date = intent.getStringExtra("EXTRA_DATE") ?: ""
        val image = intent.getStringExtra("EXTRA_IMAGE")
        val content = intent.getStringExtra("EXTRA_CONTENT") ?: "Contenido no disponible"

        setContent {
            AppV2Theme {
                NewsDetailScreen(title, date, image, content) {
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(title: String, date: String, image: String?, content: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artículo", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(image).crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(300.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(24.dp)) {
                // Etiqueta Fecha
                SuggestionChip(
                    onClick = {},
                    label = { Text(date) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Divider(modifier = Modifier.padding(vertical = 24.dp))

                // Contenido
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}