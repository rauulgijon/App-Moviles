package com.example.appv2.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appv2.data.RaceRepository
import com.example.appv2.model.Race
import com.example.appv2.model.RaceResult
import kotlinx.coroutines.launch

class RaceViewModel(application: Application) : AndroidViewModel(application) {
    var races by mutableStateOf<List<Race>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set

    var selectedRace by mutableStateOf<Race?>(null)
        private set

    // NUEVOS ESTADOS PARA RESULTADOS
    var raceResults by mutableStateOf<List<RaceResult>>(emptyList())
        private set
    var isResultsLoading by mutableStateOf(false)
        private set

    init {
        fetchRaces()
    }

    private fun fetchRaces() {
        viewModelScope.launch {
            isLoading = true
            races = RaceRepository.getRaces()
            isLoading = false
        }
    }

    // Al hacer click, guardamos la carrera Y cargamos sus resultados
    fun onRaceClicked(race: Race) {
        selectedRace = race
        loadRaceResults(race.id)
    }

    fun onBackToCalendar() {
        selectedRace = null
        raceResults = emptyList() // Limpiamos para que no salga la lista vieja al abrir otra
    }

    private fun loadRaceResults(raceId: Int) {
        viewModelScope.launch {
            isResultsLoading = true
            raceResults = RaceRepository.getRaceResults(raceId)
            isResultsLoading = false
        }
    }
}