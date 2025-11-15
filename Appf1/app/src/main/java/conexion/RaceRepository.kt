package conexion

import androidx.lifecycle.LiveData
import model.Driver
import model.Race
import model.Team

// Le pasamos los 3 DAOs en el constructor
class RaceRepository(
    private val raceDao: RaceDao,
    private val driverDao: DriverDao,
    private val teamDao: TeamDao
) {

    // Carreras
    val allRaces: LiveData<List<Race>> = raceDao.getAllRaces()
    val nextRace: LiveData<Race> = raceDao.getNextRace()
    fun getRaceById(id: Int): LiveData<Race> {
        return raceDao.getRaceById(id)
    }
    @Suppress("RedundantSuspendModifier")
    suspend fun insertAll(races: List<Race>) {
        raceDao.insertAll(races)
    }

    // ✅ AÑADIDO: Pilotos
    val allDrivers: LiveData<List<Driver>> = driverDao.getAllDrivers()
    @Suppress("RedundantSuspendModifier")
    suspend fun insertAllDrivers(drivers: List<Driver>) {
        driverDao.insertAll(drivers)
    }

    // ✅ AÑADIDO: Equipos
    val allTeams: LiveData<List<Team>> = teamDao.getAllTeams()
    @Suppress("RedundantSuspendModifier")
    suspend fun insertAllTeams(teams: List<Team>) {
        teamDao.insertAll(teams)
    }
}