package com.example.appv2.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appv2.data.TeamRepository // <--- ASEGÚRATE DE QUE ESTO ESTÉ AQUÍ
import com.example.appv2.model.Team
import kotlinx.coroutines.launch

class TeamViewModel(application: Application) : AndroidViewModel(application) {
    var teams by mutableStateOf<List<Team>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        viewModelScope.launch {
            isLoading = true
            try {
                teams = TeamRepository.getTeams()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}