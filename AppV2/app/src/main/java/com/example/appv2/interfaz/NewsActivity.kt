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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.appv2.model.News
import com.example.appv2.viewmodel.NewsViewModel
import com.example.appv2.ui.theme.AppV2Theme

class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppV2Theme {
                NewsScreen(onBack = { finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = viewModel(),
    onBack: (() -> Unit)? = null
) {
    if (onBack != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("F1 News") },
                    navigationIcon = {
                        IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver") }
                    }
                )
            }
        ) { padding ->
            NewsContent(viewModel, padding)
        }
    } else {
        NewsContent(viewModel, PaddingValues(0.dp))
    }
}

@Composable
fun NewsContent(viewModel: NewsViewModel, padding: PaddingValues) {
    if (viewModel.isLoading) {
        Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp), // Más espacio entre noticias
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
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp) // Tarjeta Alta
            .clickable {
                val intent = Intent(context, NewsDetailActivity::class.java)
                intent.putExtra("EXTRA_TITLE", news.title)
                intent.putExtra("EXTRA_DATE", news.date)
                intent.putExtra("EXTRA_IMAGE", news.image)
                intent.putExtra("EXTRA_CONTENT", news.content ?: "Lee más en la web oficial.")
                context.startActivity(intent)
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Imagen de fondo
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(news.image).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 2. Degradado oscuro
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                            startY = 300f
                        )
                    )
            )

            // 3. Textos
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                // Chip Rojo
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "F1 NEWS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = news.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DateRange, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = news.date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}