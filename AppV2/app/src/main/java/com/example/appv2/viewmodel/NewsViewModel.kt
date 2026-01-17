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
    private val repository = NewsRepository()

    var newsList by mutableStateOf<List<News>>(emptyList()); private set
    var isLoading by mutableStateOf(false); private set

    init { fetchNews() }

    fun fetchNews() {
        viewModelScope.launch {
            isLoading = true
            newsList = repository.getNews()
            isLoading = false
        }
    }
}