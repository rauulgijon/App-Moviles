package conexion

import androidx.lifecycle.LiveData
import model.Driver
import model.Race
import model.Team

/**
 * El Repositorio. Es una clase que actúa como intermediario.
 * El ViewModel NUNCA habla directamente con el DAO. Habla con el Repositorio.
 * Esto sirve para que en el futuro, si queremos coger datos de Internet (una API)
 * en lugar de la BD, solo cambiamos el Repositorio, y el ViewModel no se entera.
 */
class RaceRepository(
    private val raceDao: RaceDao,
    private val driverDao: DriverDao,
    private val teamDao: TeamDao
) {

    // --- Carreras ---
    // Simplemente "pasa" las peticiones del ViewModel al DAO correspondiente.
    val allRaces: LiveData<List<Race>> = raceDao.getAllRaces()
    val nextRace: LiveData<Race> = raceDao.getNextRace()
    fun getRaceById(id: Int): LiveData<Race> {
        return raceDao.getRaceById(id)
    }
    @Suppress("RedundantSuspendModifier")
    suspend fun insertAll(races: List<Race>) {
        raceDao.insertAll(races)
    }

    // --- Pilotos ---
    val allDrivers: LiveData<List<Driver>> = driverDao.getAllDrivers()
    @Suppress("RedundantSuspendModifier")
    suspend fun insertAllDrivers(drivers: List<Driver>) {
        driverDao.insertAll(drivers)
    }

    // --- Equipos ---
    val allTeams: LiveData<List<Team>> = teamDao.getAllTeams()
    @Suppress("RedundantSuspendModifier")
    suspend fun insertAllTeams(teams: List<Team>) {
        teamDao.insertAll(teams)
    }
}