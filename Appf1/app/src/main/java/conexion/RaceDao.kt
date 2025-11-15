package conexion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.Race

@Dao
interface RaceDao {

    @Query("SELECT * FROM races_table ORDER BY round ASC")
    fun getAllRaces(): LiveData<List<Race>>

    @Query("SELECT * FROM races_table WHERE id = :raceId")
    fun getRaceById(raceId: Int): LiveData<Race>

    // Query para WelcomeActivity: coge la primera carrera que aún no ha pasado
    @Query("SELECT * FROM races_table WHERE date >= date('now') ORDER BY date ASC LIMIT 1")
    fun getNextRace(): LiveData<Race>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(races: List<Race>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(race: Race)
}