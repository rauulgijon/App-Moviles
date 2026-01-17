package com.example.appv2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appv2.data.TeamRepository
import com.example.appv2.model.Team
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {
    private val repository = TeamRepository()

    var teams by mutableStateOf<List<Team>>(emptyList()); private set
    var isLoading by mutableStateOf(false); private set

    init { fetchTeams() }

    fun fetchTeams() {
        viewModelScope.launch {
            isLoading = true
            teams = repository.getTeams()
            isLoading = false
        }
    }
}