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

    // Lista de todas las carreras
    var races by mutableStateOf<List<Race>>(emptyList())
        private set

    // Estado de carga general
    var isLoading by mutableStateOf(true)
        private set

    // Carrera seleccionada (si es null, estamos en la lista; si tiene valor, estamos en detalles)
    var selectedRace by mutableStateOf<Race?>(null)
        private set

    // Resultados de la carrera seleccionada
    var raceResults by mutableStateOf<List<RaceResult>>(emptyList())
        private set

    // Estado de carga específico para los detalles
    var isResultsLoading by mutableStateOf(false)
        private set

    init {
        fetchRaces()
    }

    private fun fetchRaces() {
        viewModelScope.launch {
            isLoading = true
            races = repository.getRaces()
            isLoading = false
        }
    }

    // Función que se llama al hacer clic en una tarjeta de carrera
    fun onRaceClicked(race: Race) {
        selectedRace = race
        fetchResultsForRace(race.id)
    }

    // Pide los resultados al repositorio
    private fun fetchResultsForRace(raceId: Int) {
        viewModelScope.launch {
            isResultsLoading = true
            // Limpiamos resultados anteriores para que no se vean datos viejos mientras carga
            raceResults = emptyList()
            raceResults = repository.getRaceResults(raceId)
            isResultsLoading = false
        }
    }

    // Función para volver atrás (limpia la selección)
    fun onBackToCalendar() {
        selectedRace = null
        raceResults = emptyList()
    }
}