package viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import conexion.AppDatabase
import conexion.RaceRepository
import kotlinx.coroutines.launch
import model.Driver
import model.Race
import model.Team

class RaceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RaceRepository

    // LiveData para Carreras
    val allRaces: LiveData<List<Race>>
    val nextRace: LiveData<Race>

    // ✅ AÑADIDO: LiveData para Pilotos y Equipos
    val allDrivers: LiveData<List<Driver>>
    val allTeams: LiveData<List<Team>>

    init {
        // Obtenemos los 3 DAOs
        val appDb = AppDatabase.getDatabase(application)
        val raceDao = appDb.raceDao()
        val driverDao = appDb.driverDao()
        val teamDao = appDb.teamDao()

        // Creamos el Repositorio con los 3 DAOs
        repository = RaceRepository(raceDao, driverDao, teamDao)

        // Asignamos los LiveData
        allRaces = repository.allRaces
        nextRace = repository.nextRace
        allDrivers = repository.allDrivers // ✅ AÑADIDO
        allTeams = repository.allTeams     // ✅ AÑADIDO
    }

    // --- Funciones para Carreras ---
    fun getRaceById(id: Int): LiveData<Race> {
        return repository.getRaceById(id)
    }
    fun insertAll(races: List<Race>) = viewModelScope.launch {
        repository.insertAll(races)
    }

    // --- ✅ AÑADIDO: Funciones para Pilotos y Equipos ---
    fun insertAllDrivers(drivers: List<Driver>) = viewModelScope.launch {
        repository.insertAllDrivers(drivers)
    }
    fun insertAllTeams(teams: List<Team>) = viewModelScope.launch {
        repository.insertAllTeams(teams)
    }
}