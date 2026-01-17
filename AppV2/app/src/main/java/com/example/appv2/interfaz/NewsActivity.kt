package com.example.appv2.interfaz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.News
import com.example.appv2.viewmodel.NewsViewModel

// Mantenemos la Activity por si quieres abrirla desde fuera, pero no es obligatoria
class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Aquí sí pasamos el botón de volver porque es una activity separada
                NewsScreen(onBack = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = viewModel(),
    onBack: (() -> Unit)? = null // Hacemos el onBack opcional (nullable)
) {
    // Si estamos en el Home (onBack es null), no mostramos Scaffold con flecha
    // Si es Activity (onBack existe), mostramos la barra superior
    if (onBack != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Noticias F1") },
                    navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
                )
            }
        ) { padding ->
            NewsContent(viewModel, padding)
        }
    } else {
        // Modo "Pestaña del Home" (Sin top bar propia)
        NewsContent(viewModel, PaddingValues(0.dp))
    }
}

@Composable
fun NewsContent(viewModel: NewsViewModel, padding: PaddingValues) {
    if (viewModel.isLoading) {
        Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(viewModel.newsList) { news ->
                NewsItem(news)
            }
        }
    }
}

@Composable
fun NewsItem(news: News) {
    val context = LocalContext.current
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth().height(200.dp).clickable {
            try { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(news.url))) }
            catch (e: Exception) { e.printStackTrace() }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(news.image).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black), startY = 200f)))
            Column(Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                Text(news.title, style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                Text(news.date, style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
            }
        }
    }
}