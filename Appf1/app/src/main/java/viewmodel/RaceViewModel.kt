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

/**
 * El ViewModel es el "cerebro" de la UI.
 * Conecta la Vista (Activity) con los Datos (Repository).
 * NUNCA debe tener una referencia directa a una Activity (por eso hereda de AndroidViewModel).
 * Sobrevive a rotaciones de pantalla.
 */
class RaceViewModel(application: Application) : AndroidViewModel(application) {

    // Referencia al Repositorio (la única fuente de datos)
    private val repository: RaceRepository

    // --- LiveData ---
    // Las Activities "observarán" estas variables.
    // El ViewModel expone los datos que obtiene del Repositorio.
    // Son 'val' (inmutables) porque la Activity no debe poder cambiarlos.
    val allRaces: LiveData<List<Race>>
    val nextRace: LiveData<Race>
    val allDrivers: LiveData<List<Driver>>
    val allTeams: LiveData<List<Team>>

    // El bloque 'init' se ejecuta cuando se crea el ViewModel por primera vez.
    init {
        // Obtenemos los 3 DAOs (consultas) desde nuestra base de datos
        val appDb = AppDatabase.getDatabase(application)
        val raceDao = appDb.raceDao()
        val driverDao = appDb.driverDao()
        val teamDao = appDb.teamDao()

        // Creamos el Repositorio pasándole los DAOs
        repository = RaceRepository(raceDao, driverDao, teamDao)

        // Asignamos las variables LiveData del ViewModel a las del Repositorio
        allRaces = repository.allRaces
        nextRace = repository.nextRace
        allDrivers = repository.allDrivers
        allTeams = repository.allTeams
    }

    // --- Funciones de Inserción ---
    // Estas funciones son las que llaman las Activities (ej: preloadData)
    // para insertar datos.

    /**
     * Devuelve una carrera específica por su ID.
     * La Activity (GrandPrixDetail) llama a esta función.
     */
    fun getRaceById(id: Int): LiveData<Race> {
        return repository.getRaceById(id)
    }

    /**
     * Inserta una lista de carreras.
     * 'viewModelScope.launch' crea una Coroutine (un hilo secundario).
     * Esto es OBLIGATORIO porque las funciones del DAO (insertAll) son 'suspend'
     * y no pueden llamarse en el hilo principal de la UI.
     */
    fun insertAll(races: List<Race>) = viewModelScope.launch {
        repository.insertAll(races)
    }

    fun insertAllDrivers(drivers: List<Driver>) = viewModelScope.launch {
        repository.insertAllDrivers(drivers)
    }

    fun insertAllTeams(teams: List<Team>) = viewModelScope.launch {
        repository.insertAllTeams(teams)
    }
}