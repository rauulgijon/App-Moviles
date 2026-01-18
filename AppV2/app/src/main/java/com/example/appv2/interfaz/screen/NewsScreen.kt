package com.example.appv2.interfaz.screen

import android.content.Intent
import android.net.Uri
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

private const val NEWS_IMAGE_URL = "https://rqyytwpfcezjtndbjkwj.supabase.co/storage/v1/object/public/images/BBDD%20F1/noticias/"

@Composable
fun NewsScreen(viewModel: NewsViewModel = viewModel()) {
    val context = LocalContext.current
    val newsList = viewModel.newsList

    if (viewModel.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(newsList) { news ->
                NewsCard(news = news, onClick = {
                    news.url?.let { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it))) }
                })
            }
        }
    }
}

@Composable
fun NewsCard(news: News, onClick: () -> Unit) {
    val imageUrl = "${NEWS_IMAGE_URL}${news.image}"
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().height(220.dp).clickable { onClick() }
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build(),
                contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize()
            )
            Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.85f)), startY = 300f)))
            Column(Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                Text(news.title, style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                Text(news.date, style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
            }
        }
    }
}