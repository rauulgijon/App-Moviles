package com.example.appv2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appv2.data.NewsRepository
import com.example.appv2.model.News
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    var newsList by mutableStateOf<List<News>>(emptyList()); private set
    var isLoading by mutableStateOf(false); private set

    init { fetchNews() }
    // Funcion para obtener noticias
    fun fetchNews() {
        viewModelScope.launch {
            isLoading = true
            newsList = NewsRepository.getNews()
            isLoading = false
        }
    }
}