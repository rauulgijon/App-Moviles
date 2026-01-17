package com.example.appv2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appv2.data.RaceRepository
import com.example.appv2.model.Race
import com.example.appv2.model.RaceResult
import kotlinx.coroutines.launch

class RaceViewModel : ViewModel() {
    private val repository = RaceRepository()

    var races by mutableStateOf<List<Race>>(emptyList()); private set
    var isLoading by mutableStateOf(false); private set

    // Estados para el detalle
    var selectedRace by mutableStateOf<Race?>(null); private set
    var raceResults by mutableStateOf<List<RaceResult>>(emptyList()); private set
    var isResultsLoading by mutableStateOf(false); private set

    init { fetchRaces() }

    fun fetchRaces() {
        viewModelScope.launch {
            isLoading = true
            races = repository.getRaces()
            isLoading = false
        }
    }

    fun onRaceClicked(race: Race) {
        selectedRace = race
        fetchResults(race.id)
    }

    fun onBackToCalendar() {
        selectedRace = null
        raceResults = emptyList()
    }

    private fun fetchResults(raceId: Int) {
        viewModelScope.launch {
            isResultsLoading = true
            raceResults = repository.getRaceResults(raceId)
            isResultsLoading = false
        }
    }
}